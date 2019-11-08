package cn.com.qws.service.kafka;

import io.netty.buffer.ByteBufUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description kafka接口
 * @Author qinweisi
 * @Date 2019/8/22 14:12
 **/
@Service
public class KafkaService {

    private final static Logger logger = LoggerFactory.getLogger(KafkaService.class);

    @Resource(name = "myKafkaTemplate")
    private KafkaTemplate<String, byte[]> myKafkaTemplate;

    @Resource(name = "stringKafkaTemplate")
    private KafkaTemplate<String, String> stringKafkaTemplate;

    @Resource(name = "objectKafkaTemplate")
    private KafkaTemplate<String, Object> objectKafkaTemplate;


    public void push(String topic, byte[] info) {
        try {
            this.myKafkaTemplate.send(topic, info);
            logger.info("---------发送数据---信息：" + ByteBufUtil.hexDump(info) + ",长度：" + info.length + ",主题：" + topic);
        } catch (Exception e) {
            logger.error("---------发送数据失败---信息：" + ByteBufUtil.hexDump(info) + ",长度：" + info.length, e);
        }
    }


    public void push(String topic, String info) {
        try {
            this.stringKafkaTemplate.send(topic, info);
            logger.info("---------发送数据---信息：" + info + ",长度：" + info.length() + ",主题：" + topic);
        } catch (Exception e) {
            logger.error("---------发送数据失败---信息：" + info + ",长度：" + info.length(), e);
        }
    }


    public void push(String topic, Object info) {
        try {
            this.objectKafkaTemplate.send(topic, info);
            logger.info("---------发送数据---信息：" + info.toString() + ",主题：" + topic);
        } catch (Exception e) {
            logger.error("---------发送数据失败---信息：" + info.toString(), e);
        }
    }

    public void pushBatch(String topic, List<byte[]> info) {
        Map<String, Object> top = new HashMap<>();
        Message<List<byte[]>> kms = null;
        try {
            top.put("topic", topic);
            kms = new GenericMessage<List<byte[]>>(info, top);
            this.objectKafkaTemplate.setDefaultTopic(topic);
            this.objectKafkaTemplate.send(kms);
        } catch (Exception e) {
            logger.error("---------发送数据失败---信息：" + kms.toString(), e);
        }
    }

}
