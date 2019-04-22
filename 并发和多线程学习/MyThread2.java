package com;

/**
 * @author mz
 * 并发和多线程学习
 * 实现runable接口
 */
public class MyThread2 implements Runnable {
    @Override
    public void run() {
        System.out.println("thread1 is running");
    }
    public static void main(String[] args){
        Thread thread = new Thread(new MyThread2());
        thread.start();
        /**
         * runable 匿名内部类
         *
         */
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("thread2 is running");
            }
        };
        Thread thread1 = new Thread(runnable);
        thread1.start();
    }

}
