package com;

/**
 * @author mz
 * 并发行和多线程学习
 * 创建Thread的子类
 *
 */
public class MyThread extends Thread {

    @Override
    public void run() {
        System.out.println("MyThread running");
    }


    public static void  main(String[] args){

        MyThread myThread = new MyThread();
        myThread.start();
        /**
         *匿名子类实现
         */
        Thread myThread2 = new Thread(){
            @Override
            public void run() {
                System.out.println("Thread2 is running");
            }
        };
        myThread2.start();

    }
}
