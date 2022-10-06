package com.yologger.h2h.chatserver.repository.repository

import com.yologger.h2h.chatserver.config.TestRedisConfig
import com.yologger.h2h.chatserver.repository.ChatRoomRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Import(TestRedisConfig::class)
@DisplayName("ChatRoomRepository 테스트")
class ChatRoomRepositoryTest constructor(
    @Autowired private val chatRoomRepository: ChatRoomRepository
) {

    @Test
    @DisplayName("채팅방 생성과 모든 채팅방 조회")
    fun createChatRoom_findAllRoom() {
        // Given & When
        val room1 = chatRoomRepository.createChatRoom("room 1", 1)
        val room2 = chatRoomRepository.createChatRoom("room 2", 2)

        // Then
        assertThat(chatRoomRepository.findAllRoom().size).isEqualTo(2)

        chatRoomRepository.deleteRoomById(room1.roomId)
        chatRoomRepository.deleteRoomById(room2.roomId)
    }

    @Test
    @DisplayName("id로 채팅방 조회")
    fun createChatRoom_findRoomById() {
        // Given
        val roomName = "room 1"
        val ownerId = 1L
        val created = chatRoomRepository.createChatRoom(roomName, ownerId)

        // When
        val saved = chatRoomRepository.findRoomById(created.roomId)
        assertThat(saved!!.name).isEqualTo(roomName)
        assertThat(saved!!.ownerId).isEqualTo(ownerId)

        // Then
        chatRoomRepository.deleteRoomById(saved.roomId)
    }
}