package com;

/**
 * @author mz
 * 同步实例
 */
public class Conuter {
    long count = 0;
    public synchronized void add(long value){
        this.count+=value;
    }
    public static class CounterThread extends Thread{
        protected Conuter conuter;
        public CounterThread(Conuter conuter){
            this.conuter =conuter;
        }
        @Override
        public void run() {
            for (int i=0;i<10;i++){
                conuter.add(i);
            }
        }
    }


}
