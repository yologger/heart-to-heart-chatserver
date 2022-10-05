package com.yologger.h2h.chatserver.repository

import com.yologger.h2h.chatserver.config.TestMongoConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageRequest
import java.time.LocalDateTime

@DataMongoTest(excludeAutoConfiguration = [EmbeddedMongoAutoConfiguration::class])
@Import(TestMongoConfig::class)
@DisplayName("ChatMessageRepository 테스트")
class ChatMessageRepositoryTest {

    @Autowired
    lateinit var chatMessageRepository: ChatMessageRepository

    @Test
    @DisplayName("Room id로 채팅이력 조회 테스트")
    fun findAllByRoomId() {
        // Given
        val roomId = "1"
        val senderId = 1L
        val chat1 = ChatMessageDocument(roomId = roomId, senderId = senderId, message = "Hello!", date = LocalDateTime.now())
        val chat2 = ChatMessageDocument(roomId = roomId, senderId = senderId, message = "Bye!", date = LocalDateTime.now())
        chatMessageRepository.save(chat1)
        chatMessageRepository.save(chat2)

        // When
        val pageable = PageRequest.of(0, 10)
        val chats = chatMessageRepository.findAllByRoomId(roomId, pageable)

        // Then
        assertThat(chats.size).isEqualTo(2)
    }
}