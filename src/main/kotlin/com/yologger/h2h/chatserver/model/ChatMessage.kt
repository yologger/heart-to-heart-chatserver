package com.yologger.h2h.chatserver.model

import java.io.Serializable

data class ChatMessage(
    val type: MessageType,
    val roomId: String,
    val senderId: Long,
    var message: String
): Serializable {

    companion object {
        @JvmStatic private val serialVersionUID: Long = 1
    }

    enum class MessageType {
        ENTER, TALK, EXIT
    }
}
