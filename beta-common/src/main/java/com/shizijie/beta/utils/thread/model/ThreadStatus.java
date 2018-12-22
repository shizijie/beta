package com.shizijie.beta.utils.thread.model;

import lombok.Data;

@Data
public class ThreadStatus {
    private String threadName;
    private boolean finished;
    private volatile int serialNum;
    private int totalNum;
    private volatile int remainNum;
    private boolean printLog;
    public ThreadStatus(String threadName,int totalNum){
       this.threadName=threadName;
       this.finished=false;
       this.serialNum=0;
       this.totalNum=totalNum;
       this.remainNum=totalNum;
       this.printLog=true;
    }
    public int serialNumAdd(){
        synchronized (this){
            return ++this.serialNum;
        }
    }
    public int remainNumSub(){
        synchronized (this){
            return --this.remainNum;
        }
    }
    public boolean isFinished(){
        return this.remainNum==0;
    }
}
