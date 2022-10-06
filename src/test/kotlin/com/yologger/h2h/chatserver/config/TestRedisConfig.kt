package com.yologger.h2h.chatserver.config

import com.yologger.h2h.chatserver.model.ChatRoom
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import redis.embedded.RedisServer
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@TestConfiguration
class TestRedisConfig constructor(
    @Value("\${spring.redis.host}") private val host: String,
    @Value("\${spring.redis.port}") private val port: Int
) {
    private lateinit var redisServer: RedisServer

    @PostConstruct
    fun startRedis() {
        redisServer = RedisServer(port)
        redisServer.start()
    }

    @PreDestroy
    fun stopRedis() {
        redisServer.stop()
    }

    @Profile("test")
    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        val config = RedisStandaloneConfiguration();
        config.hostName = host
        config.port = port
        return LettuceConnectionFactory(config)
    }

    @Profile("test")
    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        return RedisTemplate<String, Any>().apply {
            setConnectionFactory(redisConnectionFactory())
            keySerializer = StringRedisSerializer()
            valueSerializer = Jackson2JsonRedisSerializer(ChatRoom::class.java)
            hashKeySerializer = StringRedisSerializer()
            hashValueSerializer = Jackson2JsonRedisSerializer(ChatRoom::class.java)
        }
    }
}