package com;

/**
 * @author mz
 * 创建了两个线程。他们的构造器引用同一个Counter实例。Counter.add方法是同步在实例上，是因为add方法是实例方法并且被标记上synchronized关键字。因此每次只允许一个线程调用该方法。另外一个线程必须要等到第一个线程退出add()方法时，才能继续执行方法。
 *
 * 如果两个线程引用了两个不同的Counter实例，那么他们可以同时调用add()方法。这些方法调用了不同的对象，因此这些方法也就同步在不同的对象上。这些方法调用将不会被阻塞。
 */
public class Example {
    public static void main(String[] args){
        /**
         * 阻塞
         */
        /*Conuter conuter = new Conuter();
        Thread thread1 = new Conuter.CounterThread(conuter);
        Thread thread2 = new Conuter.CounterThread(conuter);
        */
        Conuter conuter1 = new Conuter();
        Conuter conuter2 = new Conuter();
        Thread thread1 = new Conuter.CounterThread(conuter1);
        Thread thread2 = new Conuter.CounterThread(conuter2);
        thread1.start();
        thread2.start();
    }
}
