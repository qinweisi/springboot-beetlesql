package cn.com.qws.service.kafka;

import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * @Description kafka使用序列化类
 * @Author qinweisi
 * @Date 2019/8/22 14:12
 **/
public class EncodeKafka implements Serializer<Object> {
	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {

	}

	@Override
	public byte[] serialize(String topic, Object data) {
		return BeanUtils.bean2Byte(data);
	}

	/*
	 * producer调用close()方法是调用
	 */
	@Override
	public void close() {
		System.out.println("EncodeKafka is close");
	}
}