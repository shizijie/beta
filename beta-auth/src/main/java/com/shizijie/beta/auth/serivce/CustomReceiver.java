package com.shizijie.beta.auth.serivce;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.receiver.Receiver;

/**
 * @author shizijie
 * @version 2019-03-25 下午11:41
 */
@Slf4j
public class CustomReceiver extends Receiver<String> {
    public CustomReceiver(StorageLevel storageLevel) {
        super(storageLevel);
    }

    @Override
    public void onStart() {
        new Thread(this::doStart).start();
        log.info("开始启动Receiver...");
        //doStart();
    }

    public void doStart() {
        while(!isStopped()) {
            int value = RandomUtils.nextInt(100);
            if(value <20) {
                try {
                    Thread.sleep(1000);
                }catch (Exception e) {
                    log.error("sleep exception",e);
                    restart("sleep exception", e);
                }
            }
            store(String.valueOf(value));
        }
    }


    @Override
    public void onStop() {
        log.info("即将停止Receiver...");
    }
}
