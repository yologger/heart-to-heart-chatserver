package com.yologger.h2h.chatserver.config

import com.yologger.h2h.chatserver.handler.MessageHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.*

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig: WebSocketMessageBrokerConfigurer {
    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        // STOMP 연결 엔드포인트
        registry.addEndpoint("/ws")
    }

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        // 메시지 브로커(채팅방) 구독을 위한 엔드포인트
        // SimpleBroker는 인메모리 방식의 메시지 브로커
        // Redis, Kafka 같은 외부 메시지 브로커를 사용할 수도 있다.
        registry.enableSimpleBroker("/subscribe")

        // 메시지 브로커(채팅방)으로 메시지를 보내기 위한 엔드포인트
        registry.setApplicationDestinationPrefixes("/publish");
    }
}