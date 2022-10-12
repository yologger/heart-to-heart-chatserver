package com.yologger.h2h.chatserver.repository

import com.yologger.h2h.chatserver.repository.chatRoom.ChatRoomRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("local")
@DisplayName("ChatRoomRepository 테스트")
class ChatRoomRepositoryTest constructor(
    @Autowired private val chatRoomRepository: ChatRoomRepository
) {
    @Test
    @DisplayName("채팅방 생성, 조회 테스트")
    fun createChatRoom() {
        val room1Name = "my room 1"
        val owner1Id = 1L
        chatRoomRepository.createChatRoom(name = room1Name, ownerId = owner1Id)

        val room2Name = "my room 2"
        val owner2Id = 2L
        chatRoomRepository.createChatRoom(name = room2Name, ownerId = owner2Id)

        val allRooms = chatRoomRepository.findAllRoom()
        assertThat(allRooms.size).isEqualTo(2)
    }
}