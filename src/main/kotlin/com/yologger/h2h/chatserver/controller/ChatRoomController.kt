package com.yologger.h2h.chatserver.controller

import com.yologger.h2h.chatserver.model.ChatRoom
import com.yologger.h2h.chatserver.model.CreateChatRoomRequest
import com.yologger.h2h.chatserver.repository.ChatRoomRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/chat")
class ChatRoomController(
    @Autowired val chatRoomRepository: ChatRoomRepository,
) {

    // 채팅방 생성
    @PostMapping("/room")
    fun createChatRoom(@RequestBody request: CreateChatRoomRequest): ResponseEntity<ChatRoom> {
        return ResponseEntity<ChatRoom>(chatRoomRepository.createChatRoom(name = request.name, ownerId = request.ownerId), HttpStatus.CREATED)
    }

    // 모든 채팅방 조회
    @GetMapping("/room")
    fun findAllRoom(): ResponseEntity<List<ChatRoom>> {
        return ResponseEntity<List<ChatRoom>>(chatRoomRepository.findAllRoom(), HttpStatus.OK)
    }

    // Room ID로 채팅방 조회
    @GetMapping("/room/{id}")
    fun findRoomById(@PathVariable("id") id: String): ResponseEntity<ChatRoom?> {
        return ResponseEntity<ChatRoom?>(chatRoomRepository.findRoomById(id), HttpStatus.OK)
    }
}