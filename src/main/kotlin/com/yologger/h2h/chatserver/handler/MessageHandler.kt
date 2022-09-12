package com.yologger.h2h.chatserver.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.yologger.h2h.chatserver.model.ChatMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class MessageHandler constructor(
    @Autowired private val objectMapper: ObjectMapper
): TextWebSocketHandler() {
    override fun afterConnectionEstablished(session: WebSocketSession) {
        super.afterConnectionEstablished(session)
        // 연결 수립 후 채팅방 객체에 session id 저장
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val chatMessage = objectMapper.readValue(message.payload, ChatMessage::class.java)
        session.sendMessage(TextMessage(chatMessage.message))
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        super.afterConnectionClosed(session, status)
        // 연결 해제 후 채팅방 객체에서 session id 제거
    }
}