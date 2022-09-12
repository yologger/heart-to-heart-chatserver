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
