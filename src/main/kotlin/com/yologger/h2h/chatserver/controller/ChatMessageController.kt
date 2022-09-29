package com.yologger.h2h.chatserver.controller

import com.yologger.h2h.chatserver.model.ChatMessage
import com.yologger.h2h.chatserver.repository.ChatMessageRepository
import com.yologger.h2h.chatserver.repository.ChatRoomRepository
import com.yologger.h2h.chatserver.service.RedisPublisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@Controller
class ChatMessageController(
    @Autowired private val chatRoomRepository: ChatRoomRepository,
    @Autowired private val chatMessageRepository: ChatMessageRepository,
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
                message.message = "${message.senderId} exited."
                redisPublisher.publish(chatRoomRepository.getTopic(message.roomId)!!, message)
                chatRoomRepository.exitChatRoom(message.roomId)
                return
            }
        }
        redisPublisher.publish(chatRoomRepository.getTopic(message.roomId)!!, message)
    }

    @GetMapping("/messages/room/{id}")
    fun getChatMessagesByRoomId(
        @PathVariable("id") roomId: String,
        @RequestParam("page") page: Int,
        @RequestParam("size") size: Int
    ): ResponseEntity<List<ChatMessage>> {
        val pageRequest = PageRequest.of(page, size)
        return ResponseEntity(chatMessageRepository.findAllByRoomId(roomId, pageRequest).map { it.toDTO()}, HttpStatus.OK)
    }
}