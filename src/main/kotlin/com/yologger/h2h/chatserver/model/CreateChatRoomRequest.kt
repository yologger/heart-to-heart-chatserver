package com.yologger.h2h.chatserver.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateChatRoomRequest(
    @JsonProperty("name") val name: String,
    @JsonProperty("owner_id") val ownerId: Long
)