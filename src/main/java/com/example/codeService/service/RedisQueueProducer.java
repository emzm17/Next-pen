package com.example.codeService.service;

import com.example.codeService.dto.CodeDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class RedisQueueProducer {


    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

    public RedisQueueProducer(StringRedisTemplate stringRedisTemplate, ObjectMapper objectMapper) {

        this.stringRedisTemplate = stringRedisTemplate;
        this.objectMapper = objectMapper;
    }

    private static final String QUEUE_NAME = "codeQueue";

    public void sendMessageToQueue(CodeDto codeDto) throws JsonProcessingException {
        // Pushing message to Redis Queue (List data structure)
        String jsonMessage = objectMapper.writeValueAsString(codeDto);
        stringRedisTemplate.opsForList().leftPush(QUEUE_NAME, jsonMessage);
    }

}
