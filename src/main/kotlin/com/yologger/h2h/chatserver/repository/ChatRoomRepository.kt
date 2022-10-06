package com.yologger.h2h.chatserver.repository

import com.yologger.h2h.chatserver.model.ChatRoom
import com.yologger.h2h.chatserver.service.RedisSubscriber
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.stereotype.Repository
import java.util.UUID
import javax.annotation.PostConstruct

@Repository
class ChatRoomRepository constructor(
    @Autowired private val redisTemplate: RedisTemplate<String, Any>,
    @Autowired private val redisMessageListeners: RedisMessageListenerContainer,
    @Autowired private val redisSubscriber: RedisSubscriber
){
    companion object {
        const val CHAT_ROOMS_KEY = "CHAT_ROOM"
    }
    private lateinit var opsHashChatRoom: HashOperations<String, String, ChatRoom>
    private lateinit var channels: MutableMap<String, ChannelTopic>

    @PostConstruct
    fun init() {
        opsHashChatRoom = redisTemplate.opsForHash()
        channels = hashMapOf()
    }

    // 채팅방 생성
    fun createChatRoom(name: String, ownerId: Long): ChatRoom {
        val roomId = UUID.randomUUID().toString()
        val chatRoom = ChatRoom(roomId = roomId, name = name, ownerId = ownerId)
        opsHashChatRoom.put(CHAT_ROOMS_KEY, chatRoom.roomId, chatRoom)
        return chatRoom
    }

    // 모든 채팅방 조회
    fun findAllRoom(): List<ChatRoom> {
        return opsHashChatRoom.values(CHAT_ROOMS_KEY)
    }

    // ID로 채팅방 조회
    fun findRoomById(id: String): ChatRoom? {
        return opsHashChatRoom.get(CHAT_ROOMS_KEY, id)
    }

    // ID로 채팅방 삭제
    fun deleteRoomById(id: String) {
        opsHashChatRoom.delete(CHAT_ROOMS_KEY, id)
    }

    // 채팅방 참여하기
    fun enterChatRoom(roomId: String) {
        var channel = channels[roomId]
        if (channel === null) {
            channel = ChannelTopic(roomId)
            // Subscribe redis channel
            redisMessageListeners.addMessageListener(redisSubscriber, channel)
            channels[roomId] = channel
        }
    }

    // 채팅방 나가기
    fun exitChatRoom(roomId: String) {
        val channel = channels[roomId]
        if (channel !== null) {
            // Unsubscribe redis channel
            redisMessageListeners.removeMessageListener(redisSubscriber, channel)
        }
    }

    fun getChannel(roomId: String): ChannelTopic? = channels[roomId]
}