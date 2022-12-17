package com.yologger.h2h.chatserver.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import redis.embedded.RedisServer
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Configuration
@Profile("local", "test")
class EmbeddedRedisConfig (
    @Value("\${spring.redis.port}") private val port: Int
) {
    private lateinit var redisServer: RedisServer

    @PostConstruct
    fun startRedis() {
        redisServer = RedisServer(port)
        redisServer.start()
    }

    @PreDestroy
    fun stopRedis() = redisServer.stop()
}