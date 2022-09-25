package com.yologger.h2h.chatserver.controller

import com.yologger.h2h.chatserver.model.ChatMessage
import com.yologger.h2h.chatserver.repository.ChatRoomRepository
import com.yologger.h2h.chatserver.service.RedisPublisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller

@Controller
class ChatMessageController(
    @Autowired private val chatRoomRepository: ChatRoomRepository,
    @Autowired private val redisPublisher: RedisPublisher
) {
    @MessageMapping("/message")
    fun message(message: ChatMessage) {
        when (message.type) {
            ChatMessage.MessageType.ENTER -> {
                chatRoomRepository.enterChatRoom(message.roomId)
                message.message = "${message.senderId} entered."
            }
            ChatMessage.MessageType.EXIT -> {

            }
        }
        redisPublisher.publish(chatRoomRepository.getTopic(message.roomId)!!, message)
    }
}