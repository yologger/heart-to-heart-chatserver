package com.yologger.h2h.chatserver.service

import com.yologger.h2h.chatserver.model.ChatMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Service

@Service
class RedisPublisher (
    @Autowired private val redisTemplate: RedisTemplate<String, Any>,
    @Autowired private val mongoTemplate: MongoTemplate
) {
    fun publish(topic: ChannelTopic, message: ChatMessage) {
        // Redis Channel로 메시지 전송
        redisTemplate.convertAndSend(topic.topic, message)

        // Mongo DB에 메시지 저장
        mongoTemplate.save(message.toDocument())
    }
}