package com.yologger.h2h.chatserver.config

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import de.flapdoodle.embed.mongo.MongodExecutable
import de.flapdoodle.embed.mongo.MongodProcess
import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.MongodConfig
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.process.runtime.Network
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.core.MongoTemplate
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@TestConfiguration
@EnableMongoAuditing
class TestMongoConfig(
    @Value("\${spring.data.mongodb.host}") private val host: String,
    @Value("\${spring.data.mongodb.port}") private val port: Int,
    @Value("\${spring.data.mongodb.database}") private val database: String
) {
    lateinit var mongodStarter: MongodStarter
    lateinit var mongodProcess: MongodProcess
    lateinit var mongodExecutable: MongodExecutable

    @PostConstruct
    fun startMongo() {
        val mongodConfig = MongodConfig.builder()
            .version(Version.Main.PRODUCTION)
            .net(Net(host, port, Network.localhostIsIPv6()))
            .build()
        mongodStarter = MongodStarter.getDefaultInstance()
        mongodExecutable = mongodStarter.prepare(mongodConfig)
        mongodProcess = mongodExecutable.start()
    }

    @Bean
    fun mongoClient(): MongoClient {
        return MongoClients.create("mongodb://${host}:${port}")
    }

    @Bean
    fun mongoTemplate(): MongoTemplate {
        return MongoTemplate(mongoClient(), database)
    }

    @PreDestroy
    fun stopMongo() {
        mongodProcess.stop()
        mongodExecutable.stop()
    }
}