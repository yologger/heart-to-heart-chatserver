package com.yologger.h2h.chatserver.model

import com.yologger.h2h.chatserver.repository.ChatMessageDocument

data class ChatMessage(
    val type: MessageType = MessageType.TALK,
    val roomId: String,
    val senderId: Long,
    var message: String
) {
    enum class MessageType {
        ENTER, TALK, EXIT
    }

    fun toDocument(): ChatMessageDocument {
        return ChatMessageDocument(roomId = this.roomId, senderId = this.senderId, message = this.message)
    }
}
