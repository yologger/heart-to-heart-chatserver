package com.yologger.h2h.chatserver.service

import com.yologger.h2h.chatserver.model.ChatMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Service

@Service
class RedisPublisher constructor(
    @Autowired private val redisTemplate: RedisTemplate<String, Any>
) {
    fun publish(channelTopic: ChannelTopic, message: ChatMessage) {
        redisTemplate.convertAndSend(channelTopic.topic, message)
    }
}