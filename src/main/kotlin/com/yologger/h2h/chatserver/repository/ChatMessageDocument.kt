package com.yologger.h2h.chatserver.repository

import com.yologger.h2h.chatserver.model.ChatMessage
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

@Document(collection = "chatMessage")
data class ChatMessageDocument(
    @Id val id: String? = null,
    @Field val roomId: String,
    @Field val senderId: Long,
    @Field val message: String,
    @Field val date: LocalDateTime
) {
    fun toDTO(): ChatMessage {
        return ChatMessage(roomId = this.roomId, senderId = this.senderId, message = this.message, date = this.date)
    }
}