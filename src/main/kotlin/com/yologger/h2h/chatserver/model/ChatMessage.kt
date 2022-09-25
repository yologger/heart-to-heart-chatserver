package com.yologger.h2h.chatserver.model

data class ChatMessage(
    val type: MessageType,
    val roomId: String,
    val senderId: Long,
    var message: String
) {
    enum class MessageType {
        ENTER, MESSAGE, EXIT
    }
}
