package com.yologger.h2h.chatserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [EmbeddedMongoAutoConfiguration::class])
class Application
fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
