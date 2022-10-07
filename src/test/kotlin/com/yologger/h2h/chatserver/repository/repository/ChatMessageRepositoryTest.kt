package com.yologger.h2h.chatserver.repository.repository

import com.yologger.h2h.chatserver.config.TestMongoConfig
import com.yologger.h2h.chatserver.repository.ChatMessageDocument
import com.yologger.h2h.chatserver.repository.ChatMessageRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.core.MongoTemplate
import java.time.LocalDateTime

@DataMongoTest(excludeAutoConfiguration = [EmbeddedMongoAutoConfiguration::class])
@Import(TestMongoConfig::class)
@DisplayName("ChatMessageRepository 테스트")
class ChatMessageRepositoryTest constructor(
    @Autowired private val chatMessageRepository: ChatMessageRepository,
    @Autowired private val mongoTemplate: MongoTemplate
) {

    @AfterEach
    fun tearDown() {
        chatMessageRepository.deleteAll()
    }

    @Test
    @DisplayName("Room ID로 채팅 메시지 조회 테스트")
    fun findAllByRoomId() {
        // Given
        val roomId = "1"
        val senderId = 1L
        val chat1 = ChatMessageDocument(roomId = roomId, senderId = senderId, message = "Hello!")
        val chat2 = ChatMessageDocument(roomId = roomId, senderId = senderId, message = "Bye!")
        mongoTemplate.save(chat1)
        mongoTemplate.save(chat2)

        // When
        val pageable = PageRequest.of(0, 10)
        val chats = chatMessageRepository.findAllByRoomId(roomId, pageable)

        // Then
        assertThat(chats.size).isEqualTo(2)
    }
}