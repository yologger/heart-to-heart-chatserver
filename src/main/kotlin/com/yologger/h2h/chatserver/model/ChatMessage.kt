package com.yologger.h2h.chatserver.model

import com.yologger.h2h.chatserver.repository.ChatMessageDocument
import java.io.Serializable
import java.time.LocalDateTime

data class ChatMessage(
    val type: MessageType,
    val roomId: String,
    val senderId: Long,
    var message: String,
    val date: LocalDateTime
): Serializable {

    companion object {
        @JvmStatic private val serialVersionUID: Long = 1
    }

    enum class MessageType {
        ENTER, TALK, EXIT
    }

    fun toDocument(): ChatMessageDocument {
        return ChatMessageDocument(roomId = this.roomId, senderId = this.senderId, message = this.message, date = this.date)
    }
}
