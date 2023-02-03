package com.doing.hilibrary.global

import android.os.Handler
import android.os.Looper
import androidx.annotation.IntRange
import com.doing.hilibrary.log.DiLog
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.ReentrantLock

class DiExecutors private constructor() {

    companion object {
        const val TAG = "DiExecutors"

        @JvmStatic
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { DiExecutors() }
    }

    private val mExecutor: ThreadPoolExecutor
    private val mPauseLock = ReentrantLock()
    private val mCondition = mPauseLock.newCondition()

    private var isPause = false
    private val mHandler = Handler(Looper.getMainLooper());


    init {
        val cpuCount = Runtime.getRuntime().availableProcessors()
        val corePoolSize = cpuCount + 1
        val maxPoolSize = cpuCount * 2 + 1
        val blockingQueue = PriorityBlockingQueue<Runnable>()

        val counter = AtomicInteger()
        val factory = ThreadFactory { task ->
            Thread(task, "Di-Executor: ${counter.getAndDecrement()}")
        }

        val handler = RejectedExecutionHandler { task, executor ->
            DiLog.w(TAG, "task: $task 任务已经被丢弃, 任务数量: ${executor.taskCount}," +
                    "正在运行线程数量: ${executor.activeCount}")
        }

        mExecutor = object : ThreadPoolExecutor(corePoolSize, maxPoolSize, 30L, TimeUnit.SECONDS,
            blockingQueue, factory, handler) {

            override fun beforeExecute(t: Thread?, r: Runnable?) {
                if (isPause) {
                    try {
                        mPauseLock.lock()
                        mCondition.await()
                    } catch (e: Exception) {
                       DiLog.e(TAG, e, "Thread name: ${Thread.currentThread().name} is interrupted")
                    } finally {
                        mPauseLock.unlock()
                    }
                    DiLog.d(TAG, "Thread is resumed")
                }
            }

            override fun afterExecute(r: Runnable?, t: Throwable?) {
                super.afterExecute(r, t)

                //TODO 监控线程池耗时任务,线程创建数量,正在运行的数量
                DiLog.e(TAG, t, "已执行完的任务的优先级是：${(r as PriorityRunnable).priority}")

            }
        }
    }

    fun execute(task: PriorityRunnable) {
        mExecutor.execute(task)
    }

    fun execute(@IntRange(from = 0, to = 10) priority: Int, task: Runnable) {
        execute(PriorityRunnable(priority, task))
    }

    fun pause() {
        mPauseLock.lock()
        try {
            isPause = true
            DiLog.d(TAG, "hiExecutor is paused")
        } finally {
            mPauseLock.unlock()
        }
    }

    fun resume() {
        mPauseLock.lock()
        try {
            isPause = false
            mCondition.signalAll()
            DiLog.d(TAG, "hiExecutor is resume func is called")
        } finally {
            mPauseLock.unlock()
        }
    }

    fun getExecutor(): ThreadPoolExecutor {
        return mExecutor
    }

    open class PriorityRunnable(@IntRange(from = 0, to = 10) val priority: Int, private val task: Runnable)
        : Runnable, Comparable<PriorityRunnable> {

        override fun run() {
            task.run()
        }

        override fun compareTo(other: PriorityRunnable): Int {
            val minus = this.priority - other.priority
            return if (minus > 0) 1 else if (minus < 0) -1 else 0
        }

    }

    abstract open inner class Callback<T> : Runnable {
        override fun run() {
            mHandler.post { onPrepare() }

            val data = onBackground()

            mHandler.removeCallbacksAndMessages(null)
            mHandler.post { onComplete(data) }
        }

        abstract protected fun onBackground(): T
        abstract protected fun onComplete(t: T)


        protected fun onPrepare() {

        }
    }
}