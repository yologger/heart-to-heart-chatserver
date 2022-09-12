package com.yologger.h2h.chatserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HeartToHeartChatserverApplication

fun main(args: Array<String>) {
    runApplication<HeartToHeartChatserverApplication>(*args)
}
