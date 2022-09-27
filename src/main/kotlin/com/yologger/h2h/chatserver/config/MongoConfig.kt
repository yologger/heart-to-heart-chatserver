package com.yologger.h2h.chatserver.config

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.core.MongoTemplate

@Configuration
class MongoConfig(
    @Value("\${spring.data.mongodb.host}") private val host: String,
    @Value("\${spring.data.mongodb.port}") private val port: Int,
    @Value("\${spring.data.mongodb.database}") private val database: String
) : AbstractMongoClientConfiguration() {

    override fun getDatabaseName(): String {
        return database
    }

    @Bean
    override fun mongoClient(): MongoClient {
        val url = "mongodb://${host}:${port}"
        return MongoClients.create(url)
    }

    @Bean
    fun mongoTemplate(): MongoTemplate {
        return MongoTemplate(mongoClient(), database)
    }
}