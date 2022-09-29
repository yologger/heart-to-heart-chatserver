package com.yologger.h2h.chatserver.repository

import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface ChatMessageRepository: MongoRepository<ChatMessageDocument, String> {
    fun findAllByRoomId(id: String, pageable: Pageable): List<ChatMessageDocument>
}