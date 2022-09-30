package com.yologger.h2h.chatserver.repository

import com.yologger.h2h.chatserver.config.TestEmbeddedMongoConfig
import com.yologger.h2h.chatserver.config.TestEmbeddedRedisConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@Import(TestEmbeddedRedisConfig::class, TestEmbeddedMongoConfig::class)
@DisplayName("ChatRoomRepository 테스트")
@ActiveProfiles("test")
class ChatRoomRepositoryTest {

    @Autowired lateinit var chatRoomRepository: ChatRoomRepository

    @Test
    @DisplayName("채팅방 생성과 조회 테스트")
    fun createChatRoom_findRoom() {
        val roomName = "room name"
        val ownerId = 1L;

        val createdRoom = chatRoomRepository.createChatRoom(roomName, ownerId)

        assertThat(chatRoomRepository.findAllRoom().size).isEqualTo(1)
        assertThat(chatRoomRepository.findRoomById(createdRoom.roomId)!!.name).isEqualTo(roomName)
    }
}