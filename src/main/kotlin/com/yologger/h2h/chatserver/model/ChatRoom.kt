package com.yologger.h2h.chatserver.model

import java.io.Serializable
import java.util.*

data class ChatRoom(
    val roomId: String = UUID.randomUUID().toString(),
    val name: String,
    val ownerId: Long
): Serializable {
    companion object {
        @JvmStatic private val serialVersionUID: Long = 1
    }
}
