package com.doing.kt.coroutines

import android.util.Log
import kotlinx.coroutines.*
import java.net.CacheResponse
import kotlin.time.Duration

class KtCoroutine {

    companion object {
        const val TAG = "KtCoroutine"
    }

    private fun runBaseCode() {
        val job: Job = GlobalScope.launch(Dispatchers.Main) {

        }

        val defrred: Deferred<String> = GlobalScope.async(Dispatchers.IO) {
            ""
        }

        job.start() // 启动携程，一般不需要我们主动调用
//        job.join() // 类似 Thread.join()
        job.cancel() // 待验证

        defrred.start()
//        defrred.join()
        defrred.cancel()
//        defrred.await()
        val result = defrred.getCompleted()
    }

    fun startScene1() {
        val funcName = "Scene1"

        GlobalScope.launch(Dispatchers.Main) {
            Log.e(TAG, "$funcName 打印调用栈1", Throwable())

            Log.i(TAG, "Coroutine $funcName launch start on : ${Thread.currentThread().name}")
            val response1 = request1(funcName)
            Log.i(TAG, "response1 $funcName on : ${Thread.currentThread().name}")
            val response2 = request2(funcName, response1)
            Log.i(TAG, "response2 $funcName on : ${Thread.currentThread().name}")
            val response3 = request3(funcName, response2)

            Log.e(TAG, "$funcName 打印调用栈2", Throwable())
            updateUI(funcName, response3)
        }

        Log.i(TAG, "start $funcName : ${Thread.currentThread().name}")
        Log.i(TAG, "\n\n\n")
    }

    fun startScene2() {
        val funcName = "Scene2"

        GlobalScope.launch(Dispatchers.Main) {
            Log.e(TAG, "$funcName 打印调用栈1", Throwable())

            Log.i(TAG, "Coroutine $funcName launch start on : ${Thread.currentThread().name}")
            val response1 = request1(funcName)
            val deferred2 = GlobalScope.async { request2(funcName, response1) }
            val deffered3 = GlobalScope.async { request3(funcName, response1) }

            Log.i(TAG, "response1 $funcName deferred created on : ${Thread.currentThread().name}")
            val response2 = deferred2.await()
            Log.i(TAG, "response2 $funcName await deferred1 on : ${Thread.currentThread().name}")
            val response3 = deffered3.await()
            Log.i(TAG, "response3 $funcName await deferred2 on : ${Thread.currentThread().name}")

            Log.e(TAG, "$funcName 打印调用栈2", Throwable())
            updateUI(funcName, deferred2.await() + deffered3.await())
        }

        Log.i(TAG, "start $funcName : ${Thread.currentThread().name}")
        Log.i(TAG, "\n\n\n")
    }

    private fun updateUI(func: String, response: String) {
        Log.i(TAG, "updateUI on : ${Thread.currentThread().name}")

    }

    private suspend fun request1(func: String): String {
        delay(2000)

        val curThread = Thread.currentThread()
        Log.d(TAG, "request1 $func work on ${curThread.name} : ${curThread.hashCode()}")
        return "request1 mission completed"
    }

    private suspend fun request2(func: String, arg: String): String {
        delay(3000)

        val curThread = Thread.currentThread()
        Log.d(TAG, "request2 $func work on ${curThread.name} : ${curThread.hashCode()}")
        return "request2 mission completed"
    }

    private suspend fun request3(func: String, arg: String): String {
        delay(3000)

        val curThread = Thread.currentThread()
        Log.d(TAG, "request3 $func work on ${curThread.name} : ${curThread.hashCode()}")
        return "request3 mission completed"
    }
}