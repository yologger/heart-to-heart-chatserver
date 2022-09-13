package com.yologger.h2h.chatserver.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.yologger.h2h.chatserver.model.ChatRoom
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ChatService constructor(
    @Autowired val objectMapper: ObjectMapper
) {
    lateinit var chatRooms: MutableMap<String, ChatRoom>

    init {
        // 데이터베이스에서 채팅방 목록 조회
        chatRooms = mutableMapOf()
    }

    public fun createChatRoom(name: String): ChatRoom {
        val randomId = UUID.randomUUID().toString()
        val chatRoom = ChatRoom(roomId = randomId, name = name)
        chatRooms[randomId] = chatRoom
        return chatRoom
    }

    public fun findRoomById(roomId: String): ChatRoom? {
        return chatRooms[roomId]
    }
}