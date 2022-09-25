package com.yologger.h2h.chatserver.model

import org.springframework.web.socket.WebSocketSession
import java.io.Serializable
import java.util.*
import kotlin.collections.LinkedHashSet

data class ChatRoom(
    val roomId: String = UUID.randomUUID().toString(),
    val name: String,
    val ownerId: Long
): Serializable {
    companion object {
        @JvmStatic private val serialVersionUID: Long = 1
    }
}
