package com.yologger.h2h.chatserver.integration

import com.yologger.h2h.chatserver.model.ChatRoom
import com.yologger.h2h.chatserver.model.CreateChatRoomRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfSystemProperty
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles

@EnabledIfSystemProperty(named = "spring.profiles.active", matches = "local")
@ActiveProfiles("local")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude = [EmbeddedMongoAutoConfiguration::class])
@DisplayName("ChatRoom 통합 테스트")
class ChatRoomIntegrationTest constructor(
    @LocalServerPort private val port: Int,
    @Autowired private val restTemplate: TestRestTemplate
) {

    @Test
    @DisplayName("채팅방 생성 테스트")
    fun createChatRoom() {
        val chatRoomName = "chat room"
        val ownerId = 1L
        val request = CreateChatRoomRequest(name = chatRoomName, ownerId = ownerId)
        val response = restTemplate.postForEntity("/chat/room", request, ChatRoom::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
        response.body?.let {
            assertThat(it.name).isEqualTo(chatRoomName)
            assertThat(it.ownerId).isEqualTo(ownerId)
            assertThat(it.roomId).isNotNull()
        } ?: run {
            fail(Exception("Error"))
        }
    }
}