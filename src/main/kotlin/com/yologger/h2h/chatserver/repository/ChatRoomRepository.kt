package com.yologger.h2h.chatserver.repository

import com.yologger.h2h.chatserver.model.ChatRoom
import com.yologger.h2h.chatserver.service.RedisSubscriber
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.stereotype.Repository
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
    private lateinit var topics: MutableMap<String, ChannelTopic>

    @PostConstruct
    fun init() {
        opsHashChatRoom = redisTemplate.opsForHash()
        topics = hashMapOf()
    }

    // 채팅방 생성
    fun createChatRoom(name: String, ownerId: Long): ChatRoom {
        val chatRoom = ChatRoom(name = name, ownerId = ownerId)
        opsHashChatRoom.put(CHAT_ROOMS_KEY, chatRoom.roomId, chatRoom)
        return chatRoom
    }

    // 채팅방 참여
    fun enterChatRoom(roomId: String) {
        var topic = topics.get(roomId)
        if (topic === null) {
            topic = ChannelTopic(roomId)
            // Redis Topic을 구독하도록 추가
            redisMessageListeners.addMessageListener(redisSubscriber, topic)
            topics[roomId] = topic
        }
    }

    fun getTopic(roomId: String): ChannelTopic? = topics[roomId]
}