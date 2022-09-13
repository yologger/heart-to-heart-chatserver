package com.yologger.h2h.chatserver.model

import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession

data class ChatRoom(
    val roomId: String,
    val name: String,
    val sessions: Set<WebSocketSession>
) {
    public fun sendMessage(message: TextMessage) {
        for (session in sessions) session.sendMessage(message)
    }
}