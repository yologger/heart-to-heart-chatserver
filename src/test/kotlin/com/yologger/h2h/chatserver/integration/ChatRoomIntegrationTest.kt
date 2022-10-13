package com.yologger.h2h.chatserver.integration

import com.yologger.h2h.chatserver.model.ChatRoom
import com.yologger.h2h.chatserver.model.CreateChatRoomRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfSystemProperty
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles

@EnabledIfSystemProperty(named = "spring.profiles.active", matches = "local")
@ActiveProfiles("local")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@DisplayName("ChatRoom 통합 테스트")
class ChatRoomIntegrationTest constructor(
    @LocalServerPort private val port: Int,
    @Autowired private val restTemplate: TestRestTemplate
) {

    @Test
    @DisplayName("채팅방 생성, 모든 채팅방 조회, 채팅방 삭제 테스트")
    fun createChatRoom_findAllRooms() {
        val chatRoomName = "chat room"
        val ownerId = 1L
        val request = CreateChatRoomRequest(name = chatRoomName, ownerId = ownerId)
        val response = restTemplate.postForEntity("/chat/room", request, ChatRoom::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
        response.body?.let {
            assertThat(it.name).isEqualTo(chatRoomName)
            assertThat(it.ownerId).isEqualTo(ownerId)
            assertThat(it.roomId).isNotNull()

            val responseEntity = restTemplate.getForEntity("/chat/rooms", Array<ChatRoom>::class.java)
            assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(responseEntity.body).isNotNull
            assertThat(responseEntity.body!!.size).isEqualTo(1)

            val deleteResponse = restTemplate.exchange("/chat/room/" + it.roomId, HttpMethod.DELETE, HttpEntity.EMPTY, Long::class.java)
            assertThat(deleteResponse.body).isEqualTo(1)
        } ?: run {
            fail(Exception("Error"))
        }
    }

    @Test
    @DisplayName("채팅방 생성, ID로 채팅방 조회, 채팅방 삭제 테스트")
    fun createChatRoom_findRoomById() {
        val chatRoomName = "chat room"
        val ownerId = 1L
        val request = CreateChatRoomRequest(name = chatRoomName, ownerId = ownerId)
        val response = restTemplate.postForEntity("/chat/room", request, ChatRoom::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
        response.body?.let {
            assertThat(it.name).isEqualTo(chatRoomName)
            assertThat(it.ownerId).isEqualTo(ownerId)
            assertThat(it.roomId).isNotNull()

            val responseEntity = restTemplate.getForEntity("/chat/room/" + it.roomId, ChatRoom::class.java)
            assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(responseEntity.body).isNotNull
            assertThat(responseEntity.body!!.name).isEqualTo(chatRoomName)
            assertThat(responseEntity.body!!.ownerId).isEqualTo(ownerId)

            val deleteResponse = restTemplate.exchange("/chat/room/" + it.roomId, HttpMethod.DELETE, HttpEntity.EMPTY, Long::class.java)
            assertThat(deleteResponse.body).isEqualTo(1)

        } ?: run {
            fail(Exception("Error"))
        }
    }
}