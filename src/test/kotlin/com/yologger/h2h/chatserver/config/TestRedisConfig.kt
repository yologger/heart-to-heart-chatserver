package com.yologger.h2h.chatserver.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.serializer.StringRedisSerializer
import redis.embedded.RedisServer
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@TestConfiguration
class TestRedisConfig constructor(
    @Value("\${spring.redis.host}") private val redisHost: String,
    @Value("\${spring.redis.port}") private val redisPort: Int
) {
    private var redisServer: RedisServer? = null

    @PostConstruct
    private fun startRedis() {
        redisServer = RedisServer.builder()
            .port(redisPort)
            .build()
        redisServer?.start()
    }

    @PreDestroy
    private fun stopRedis() = redisServer?.stop()

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory = LettuceConnectionFactory(redisHost, redisPort)

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> = RedisTemplate<String, Any>().apply {
        setConnectionFactory(redisConnectionFactory())
        keySerializer = StringRedisSerializer()
        valueSerializer = StringRedisSerializer()
    }

    @Bean
    fun redisMessageListeners(redisConnectionFactory: RedisConnectionFactory): RedisMessageListenerContainer {
        return RedisMessageListenerContainer().apply {
            setConnectionFactory(redisConnectionFactory)
        }
    }
}