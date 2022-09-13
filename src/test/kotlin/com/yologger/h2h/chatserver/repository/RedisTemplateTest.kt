package com.yologger.h2h.chatserver.repository

import com.yologger.h2h.chatserver.config.EmbeddedRedisConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest
import org.springframework.context.annotation.Import
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations

@DataRedisTest
@Import(EmbeddedRedisConfig::class)
class RedisTemplateTest {

    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, Any>

    @Test
    @DisplayName("RedisTemplate opsForValue() 테스트")
    fun opsForValue() {
        val operation = redisTemplate.opsForValue()
        operation.set("name", "Paul")
        val saved = operation.get("name") as String
        assertThat(saved).isEqualTo(saved)
    }
}