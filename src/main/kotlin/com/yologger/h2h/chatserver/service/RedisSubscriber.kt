package com.yologger.h2h.chatserver.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.yologger.h2h.chatserver.model.ChatMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Service

@Service
class RedisSubscriber(
    @Autowired private val objectMapper: ObjectMapper,
    @Autowired private val redisTemplate: RedisTemplate<String, Any>,
    @Autowired private val simpMessageSendingOperations: SimpMessageSendingOperations
) : MessageListener {
    override fun onMessage(message: Message, pattern: ByteArray?) {
        // 받은 메시지 역직렬화
        val messageString = redisTemplate.stringSerializer.deserialize(message.body)
        val chatMessage = objectMapper.readValue(messageString, ChatMessage::class.java)

        // WebSocket Session으로 메시지 푸시
        simpMessageSendingOperations.convertAndSend("/subscribe/room/${chatMessage.roomId}", chatMessage)
    }
}