package com.yologger.h2h.chatserver.repository

import com.yologger.h2h.chatserver.config.TestMongoConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.core.MongoTemplate
import java.time.LocalDateTime
import java.util.*

@DataMongoTest(excludeAutoConfiguration = [EmbeddedMongoAutoConfiguration::class])
@Import(TestMongoConfig::class)
class MongoTemplateTest {

    @Autowired lateinit var mongoTemplate: MongoTemplate

    @Test
    fun saveTest() {
        val roomId = UUID.randomUUID().toString()
        val senderId = 123L
        val message = "Hello World!"
        val chatMessage = ChatMessageDocument(roomId = roomId, senderId = senderId, message = message, date = LocalDateTime.now())

        val inserted = mongoTemplate.insert(chatMessage)
        assertThat(inserted.message).isEqualTo(message)
    }
}