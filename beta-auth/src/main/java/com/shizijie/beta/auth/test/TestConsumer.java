package com.shizijie.beta.auth.test;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author shizijie
 * @version 2019-03-06 上午12:36
 */
@Component
public class TestConsumer {
//    @KafkaListener(topics = "xlkafka")
//    public void listen (ConsumerRecord<?, ?> record) throws Exception {
//        System.out.printf("topic = %s, offset = %d, value = %s \n", record.topic(), record.offset(), record.value());
//    }
}
