package cn.com.qws.job;

import cn.com.qws.service.kafka.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.UUID;

/**
 * @Description TODO
 * @Author qinweisi
 * @Date 2019/8/22 14:12
 **/
@Component
@EnableScheduling
public class Test {

    @Autowired
    private KafkaService kafkaService;

    /**
     * 定时任务
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void send() {
        String message = UUID.randomUUID().toString();
        kafkaService.push("test", message);
    }
}
