package com.yologger.h2h.chatserver.repository

import com.yologger.h2h.chatserver.config.TestEmbeddedRedisConfig
import com.yologger.h2h.chatserver.model.ChatRoom
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest
import org.springframework.context.annotation.Import
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate

@DataRedisTest
@Import(TestEmbeddedRedisConfig::class)
@DisplayName("RedisTemplate 테스트")
class RedisTemplateTest {

    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, Any>

    @Test
    @DisplayName("opsForValue() 테스트")
    fun opsForValue() {
        val operation = redisTemplate.opsForValue()
        operation.set("name", "Paul")
        val saved = operation.get("name") as String
        assertThat(saved).isEqualTo(saved)
    }

    @Test
    @DisplayName("opsForHash() 테스트")
    fun opsForHash() {
        val roomName = "testRoom"
        val ownerId = 1L
        val chatRoom = ChatRoom(name = roomName, ownerId = ownerId)

        val operation: HashOperations<String, String, ChatRoom> = redisTemplate.opsForHash()
        val key = "key"
        operation.put(key, chatRoom.roomId, chatRoom)

        val saved = operation.get(key, chatRoom.roomId)
        assertThat(saved!!.name).isEqualTo(roomName)
        assertThat(saved!!.ownerId).isEqualTo(ownerId)
    }
}