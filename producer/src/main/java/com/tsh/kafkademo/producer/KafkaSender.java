package com.tsh.kafkademo.producer;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class KafkaSender<T> {

    private Logger logger = LoggerFactory.getLogger(KafkaSender.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * kafka 发送消息
     *
     * @param obj 消息对象
     */
    public void send(T obj) {
        String jsonObj = JSON.toJSONString(obj);
        logger.info("要发送的消息： {}", jsonObj);

        //发送消息
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("kafka.tut", jsonObj);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                logger.info("Producer: 消息发送失败。Error:" + throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> sendResult) {
                //TODO 业务处理
                logger.info("Producer: 消息发送成功:");
                logger.info("Producer: 消息发送结果: " + sendResult.toString());
            }
        });
    }
}