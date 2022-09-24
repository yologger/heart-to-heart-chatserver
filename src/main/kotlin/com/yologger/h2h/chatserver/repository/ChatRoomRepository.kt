package com.yologger.h2h.chatserver.repository

import com.yologger.h2h.chatserver.model.ChatRoom
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import javax.annotation.PostConstruct

@Repository
class ChatRoomRepository constructor(
    @Autowired private val redisTemplate: RedisTemplate<String, Any>,
){
    companion object {
        const val CHAT_ROOMS_KEY = "CHAT_ROOM"
    }
    private lateinit var opsHashChatRoom: HashOperations<String, String, ChatRoom>

    @PostConstruct
    fun init() {
        opsHashChatRoom = redisTemplate.opsForHash()
    }

    // 채팅방 생성
    fun createChatRoom(name: String, ownerId: Long): ChatRoom {
        val chatRoom = ChatRoom(name = name, ownerId = ownerId)
        // 채팅방 공유를 위해 Redis Hash에 저장
        opsHashChatRoom.put(CHAT_ROOMS_KEY, chatRoom.roomId, chatRoom)
        return chatRoom
    }
}