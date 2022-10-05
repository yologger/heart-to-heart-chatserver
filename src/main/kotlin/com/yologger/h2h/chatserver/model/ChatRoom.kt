package com.yologger.h2h.chatserver.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class ChatRoom (
    @JsonProperty("roomId") val roomId: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("ownerId") val ownerId: Long
)
