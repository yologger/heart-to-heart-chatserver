package com.yologger.h2h.chatserver.repository.template

import com.yologger.h2h.chatserver.config.TestRedisConfig
import com.yologger.h2h.chatserver.model.ChatRoom
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest
import org.springframework.context.annotation.Import
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.test.context.ActiveProfiles
import java.util.*

@DataRedisTest
@Import(TestRedisConfig::class)
@DisplayName("RedisTemplate 테스트")
@ActiveProfiles("test")
class RedisTemplateTest constructor(
    @Autowired private val redisTemplate: RedisTemplate<String, Any>
) {
    @AfterEach
    fun tearDown() {
        redisTemplate.connectionFactory?.connection?.flushAll()
    }

    @Test
    @DisplayName("채팅방 생성, roomId로 채팅방 조회 테스트")
    fun createChatRoom_findChatRoom() {
        // Given
        val key = "CHAT_ROOM"
        val roomId = UUID.randomUUID().toString()
        val roomName = "testRoom"
        val ownerId = 1L
        val chatRoom = ChatRoom(roomId = roomId, name = roomName, ownerId = ownerId)

        // When
        val operation: HashOperations<String, String, ChatRoom> = redisTemplate.opsForHash()
        operation.put(key, chatRoom.roomId, chatRoom)

        // Then
        val saved = operation.get(key, chatRoom.roomId)
        assertThat(saved!!.name).isEqualTo(roomName)
        assertThat(saved!!.ownerId).isEqualTo(ownerId)
    }

    @Test
    @DisplayName("채팅방 생성, 모든 채팅방 조회 테스트")
    fun createChatRoom_findAllChatRoom() {

        // Given
        val key = "CHAT_ROOM"

        val room1Id = UUID.randomUUID().toString()
        val room1Name = "ROOM_1"
        val owner1Id = 1L
        val chatRoom1 = ChatRoom(roomId = room1Id, name = room1Name, ownerId = owner1Id)

        val room2Id = UUID.randomUUID().toString()
        val room2Name = "ROOM_2"
        val owner2Id = 2L
        val chatRoom2 = ChatRoom(roomId = room2Id, name = room2Name, ownerId = owner2Id)

        // When
        val operation: HashOperations<String, String, ChatRoom> = redisTemplate.opsForHash()
        operation.put(key, chatRoom1.roomId, chatRoom1)
        operation.put(key, chatRoom2.roomId, chatRoom2)

        // Then
        assertThat(operation.size(key)).isEqualTo(2)
    }
}