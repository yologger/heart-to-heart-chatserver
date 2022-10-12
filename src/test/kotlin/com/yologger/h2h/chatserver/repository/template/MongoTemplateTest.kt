package com.yologger.h2h.chatserver.repository.template

import com.yologger.h2h.chatserver.config.TestMongoConfig
import com.yologger.h2h.chatserver.repository.chatMessage.ChatMessageDocument
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.core.MongoTemplate
import java.util.*

@DataMongoTest(excludeAutoConfiguration = [EmbeddedMongoAutoConfiguration::class])
@Import(TestMongoConfig::class)
@DisplayName("MongoTemplate 테스트")
class MongoTemplateTest constructor(
    @Autowired val mongoTemplate: MongoTemplate
) {
    @Test
    @DisplayName("채팅 메시지 저장")
    fun saveChatMessage() {
        // Given
        val roomId = UUID.randomUUID().toString()
        val senderId = 123L
        val message = "Hello World!"
        val chatMessage = ChatMessageDocument(roomId = roomId, senderId = senderId, message = message)
        val inserted = mongoTemplate.insert(chatMessage)

        assertThat(inserted.message).isEqualTo(message)
    }
}