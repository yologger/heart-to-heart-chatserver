# Heart to Heart API Server
이 프로젝트는 `Heart to Heart` 어플리케이션의 채팅 서비스를 위한 메시징 서버입니다. `Heart to Heart` 안드로이드 어플리케이션은 [이 곳](https://github.com/yologger/heart-to-heart-android)에서 확인할 수 있습니다.

## 기술 스택
- Kotlin
- Spring Boot
- Spring WebSocket
- Spring Test
- Redis
- MySQL
- AWS EC2
- AWS ElasticCache for Redis
- Firebase Cloud Messaging
- Docker
- Kubernetes
- Kustomize
- Argo CD

## 아키텍처
메신저 서비스를 구성하는 요소는 크게 다음과 같습니다.

- 채팅 서버
- 메시지 브로커
- 채팅 내역 백업 스토리지
- 푸시 알림 서버
- 로드 밸런서
- 회원가입, 인증, 채팅이력 조회 같은 기타 API

구성 요소들을 고려한 대략적인 아키텍처를 그림으로 표현하면 다음과 같습니다.
![](./imgs/1.png)


### 채팅 서버
채팅 서버는 웹소켓 서버를 사용하여 구축할 수 있습니다.
#### WebSocket
`웹소켓(WebSocket)`은 클라이언트와 서버 사이의 양방향 통신을 지원하는 프로토콜입니다. 스프링부트 프로젝트에서 웹 소켓을 사용하려면 다음 의존성을 추가해야합니다.
``` groovy
// build.gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-websocket'
}
```
그 다음 인터페이스 `WebSocketHandler`의 구현체를 구현합니다. 이 구현체에는 웹소켓 세션 연결, 종료, 메시지 수신 시 호출되는 콜백을 오버라이드합니다.
```kotlin
@Component
class MessageHandler constructor(
    @Autowired private val objectMapper: ObjectMapper
): TextWebSocketHandler() {
    override fun afterConnectionEstablished(session: WebSocketSession) {
        super.afterConnectionEstablished(session)
        // 연결 수립 후 채팅방 객체에 session id 저장
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        session.sendMessage(TextMessage("Echo: ${chatMessage.payload}"))
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        super.afterConnectionClosed(session, status)
        // 연결 해제 후 채팅방 객체에서 session id 제거
    }
}
```
클라이언트와의 연결이 수립되면 서버는 `WebSocketSession` 객체를 메모리에 유지합니다. 이 객체에는 고유한 세션 ID와 클라이언트 정보 등이 저장되어있으며, `sendMessage()` 메소드로 클라이언트에게 메시지를 전송할 수도 있습니다.

이 구현체를 스프링 컨테이너에 빈으로 등록합니다.
```kotlin
@Configuration
@EnableWebSocket
class WebSocketConfig constructor(
    @Autowired private val messageHandler: MessageHandler
): WebSocketConfigurer {

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(messageHandler, "/message")
    }
}
```