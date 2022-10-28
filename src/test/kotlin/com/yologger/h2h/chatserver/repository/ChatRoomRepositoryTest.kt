package com.yologger.h2h.chatserver.repository

import com.yologger.h2h.chatserver.config.TestRedisConfig
import com.yologger.h2h.chatserver.repository.chatRoom.ChatRoomRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    properties = ["spring.redis.port=16379"]
)
@ActiveProfiles("test")
@DisplayName("ChatRoomRepository 테스트")
class ChatRoomRepositoryTest constructor(
    @Autowired private val chatRoomRepository: ChatRoomRepository
) {
    @Test
    @DisplayName("채팅방 생성, 모든 채팅방 조회 테스트")
    fun createChatRoom_findAllRoom() {
        val room1Name = "my room 1"
        val owner1Id = 1L
        chatRoomRepository.createChatRoom(name = room1Name, ownerId = owner1Id)

        val room2Name = "my room 2"
        val owner2Id = 2L
        chatRoomRepository.createChatRoom(name = room2Name, ownerId = owner2Id)

        val allRooms = chatRoomRepository.findAllRooms()
        assertThat(allRooms.size).isEqualTo(2)
    }

    @Test
    @DisplayName("채팅방 생성, ID로 채팅방 조회 테스트")
    fun createChatRoom_findRoomById() {
        val room1Name = "my room 1"
        val owner1Id = 1L
        val createdRoom = chatRoomRepository.createChatRoom(name = room1Name, ownerId = owner1Id)

        val queriedRoom = chatRoomRepository.findRoomById(createdRoom.roomId)
        queriedRoom?.let {
            assertThat(it.name).isEqualTo(room1Name)
            assertThat(it.ownerId).isEqualTo(owner1Id)
        } ?: run {
            fail()
        }
    }
}