package com.doing.java.concurrent.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConsumerProducer {

    private static class WorkTask {

        private AtomicInteger bread = new AtomicInteger(0);

        private final ReentrantLock lock = new ReentrantLock();
        private final Condition condition1 = lock.newCondition();
        private final Condition condition2 = lock.newCondition();


        public void producer() throws InterruptedException {
            while (true) {
                Thread.sleep(1000);
                bread.getAndIncrement();
                System.out.println(bread.get() + ": 生产一片面包 1s");

                try {
                    lock.lock();
                    condition2.signalAll();
                    while (bread.get() >= 10) {
                        condition1.await();
                    }
                } finally {
                    lock.unlock();
                }

            }
        }

        public void consumer1() throws InterruptedException {
            while (true) {
                try {
                    lock.lock();
                    if (bread.get() < 10) {
                        condition1.signal();
                    }

                    while (bread.get() <= 0) {
                        condition2.await();
                    }
                } finally {
                    lock.unlock();
                }

                Thread.sleep(2000);
                bread.getAndDecrement();
                System.out.println(bread.get() + ": 消费一片面包 2s");
            }
        }

        public void consumer2() throws InterruptedException {
            while (true) {
                try {
                    lock.lock();
                    if (bread.get() < 10) {
                        condition1.signal();
                    }

                    while (bread.get() <= 0) {
                        condition2.await();
                    }

                } finally {
                    lock.unlock();
                }

                Thread.sleep(3000);
                bread.getAndDecrement();
                System.out.println(bread.get() + ": 消费一片面包 3s");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final WorkTask workTask = new WorkTask();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    workTask.producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    workTask.consumer1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    workTask.consumer2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

    }
}
