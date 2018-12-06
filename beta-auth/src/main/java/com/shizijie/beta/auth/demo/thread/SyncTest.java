package com.shizijie.beta.auth.demo.thread;

public class SyncTest implements Runnable {
    static int i=0;
    static Object lock=new Object();
    public void increase(){
        i++;
    }
    @Override
    public void run() {
        for(int j=0;j<1000000;j++){
            synchronized (lock){
                increase();
                System.out.println(Thread.currentThread().getName()+"----"+i);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(new SyncTest());
        Thread t2=new Thread(new SyncTest());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
