# Heart to Heart API Server
이 프로젝트는 `Heart to Heart` 어플리케이션의 채팅 서비스를 위한 메시징 서버입니다. `Heart to Heart` 안드로이드 어플리케이션은 [이 곳](https://github.com/yologger/heart-to-heart-android)에서 확인할 수 있습니다.

## 기술 스택
- Kotlin
- Spring Boot
- Spring WebSocket
- Spring Test
- AWS ElasticCache for Redis (alpha, prod)
- Embedded Redis (local, test)
- Mongo DB
- Firebase Cloud Messaging

## 아키텍처
메신저 서비스를 구성하는 요소는 크게 다음과 같습니다.

- 채팅 서버
- 메시지 브로커
- 채팅 내역 백업 스토리지
- 푸시 알림 서버
- 로드 밸런서
- 회원가입, 인증, 채팅이력 조회 같은 기타 API

![](./imgs/a.png)

## 인프라

- Docker
- Kubernetes (AWS EKS)
- Argo CD
- AWS ECR
- AWS EC2
- AWS Elastic Load Balancer (NLB)
- Kustomize

![](./imgs/b.png)

## 기능
- [x] 다중 채팅 서버 with Redis
- [x] 채팅방 생성
- [x] 채팅방 입장
- [x] 채팅방 메시지 브로드캐스팅
- [x] 채팅방 퇴장
- [ ] 운영 환경 구축 (AWS, Kubernetes)
- [ ] CI/CD 구축 (GitHub Actions, AWS ECR, Argo CD)
- [ ] 채팅 이력 백업 
- [ ] 로드 밸런싱 (AWS Elastic Load Balancer)
- [ ] Push Notification (Firebase Cloud Messaging)