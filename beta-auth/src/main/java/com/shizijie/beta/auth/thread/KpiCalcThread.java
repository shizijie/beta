package com.shizijie.beta.auth.thread;

/**
 * @author shizijie
 * @version 2018-08-19 下午10:28
 */
public class KpiCalcThread implements Runnable{
    private  int i;
    public KpiCalcThread(int i){
        this.i=i;
    }
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        System.out.println(i);
        String str=null;
        System.out.println(str.equals("1"));
        try {
            System.out.println(1/0);
        }catch (Exception e){
            System.out.println("异常");
        }

    }

}
