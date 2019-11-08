package cn.com.qws.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 监听
 */
public class KafkaListeners {

    @KafkaListener(topics = {"test"})
    public void listener(ConsumerRecord<?, ?> record) throws Exception {
        String topic = record.topic();
        Object message = record.value();
        System.out.println("--------kafka接收数据----主题：" + topic + "{}内容：" + message);
    }
}
