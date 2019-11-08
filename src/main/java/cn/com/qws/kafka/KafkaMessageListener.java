package cn.com.qws.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

//@Component
//public class KafkaMessageListener implements AcknowledgingMessageListener<String, Object> {
//
//	private static final Logger logger = LoggerFactory.getLogger(KafkaMessageListener.class);
//
//	@Override
//	public void onMessage(final ConsumerRecord<String, Object> message, final Acknowledgment acknowledgment) {
//		Object msg =   message.value();
//		String topic = message.topic();
//		logger.info("==============kafka消费数据==========主题：" + topic + ",内容：" + msg);
//		acknowledgment.acknowledge();// 调用acknowledgment的ack方法，提交offset
//	}
//}
