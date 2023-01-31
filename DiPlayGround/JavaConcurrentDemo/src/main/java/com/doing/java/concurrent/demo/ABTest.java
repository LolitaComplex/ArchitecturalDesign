package com.doing.java.concurrent.demo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ABTest {

    private static class ThreadTask {

        private static Object lock = new Object();
//        private static AtomicInteger counter = new AtomicInteger(0);
        private static int counter = 0;
        private static volatile boolean isAStart = false;

        public void printA() {
            synchronized (lock) {
                while (counter++ < 10) {
                    System.out.println("打印A");
                    isAStart = true;
                    lock.notify();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notify();
            }
        }

        private void printB() {
            synchronized (lock) {
                if (!isAStart) {
                    try {
                        System.out.println("B wait1");
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                while (counter++ < 10) {
                    System.out.println("打印B");
                    lock.notify();
                    try {
                        System.out.println("B wait2");
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                new ThreadTask().printA();
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                new ThreadTask().printB();
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("哈哈哈 结束了");
    }
}
