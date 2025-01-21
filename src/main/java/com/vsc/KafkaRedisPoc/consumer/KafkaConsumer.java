package com.vsc.KafkaRedisPoc.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class KafkaConsumer {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public KafkaConsumer(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //kafka message listener
    @KafkaListener(topics = "sample_topic",
            groupId = "group_id")

    // Method
    public void consume(@Payload String message,
                        @Header(KafkaHeaders.OFFSET) Long offset,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        // Print statement for listened kafka message
        System.out.println("message = " + message + " offset = "+ offset + " partition = "+ partition);

        //creating object for pushing on stream
        Map<Object, Object> objectObjectMap = new HashMap<>();
        objectObjectMap.put(offset, message);

        //pushing on redis stream
        String key = "localKey";
        redisTemplate.opsForStream().add(key, objectObjectMap);
        System.out.println("done on redis stream");
    }
}
