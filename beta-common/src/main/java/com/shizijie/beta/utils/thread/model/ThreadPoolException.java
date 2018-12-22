package com.shizijie.beta.utils.thread.model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadPoolException implements Thread.UncaughtExceptionHandler{
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error(e.getClass().getSimpleName()+"-"+e.getMessage(),e);
    }
}
