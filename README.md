# Retry View
제품에 대한 리뷰를 작성하고, 중고 물품 거래시 해당 물품의 리뷰도 확인할 수 있으며 사용자나 관리자끼리 실시간으로 메시지를 주고받으며 대화할 수 있는 서비스 API 개발 프로젝트입니다.

![RetryView drawio](https://github.com/user-attachments/assets/fae0fc9c-eedd-4855-8991-66b4b678046b)


### 기간
2024.09 ~ (진행중)

### 기술
Java, SpringBoot, JPA, Redis, MySQL, Docker, Jenkins, AWS

### Swagger 주소
(링크)

### OAuth2를 이용한 SNS로그인
일반적인 회원가입 외에도 Spring Security, OAith2를 이용하여 구글, 네이버 계정을 연동하여 회원가입/로그인을 할 수 있도록 구현하였습니다.

### 잦은 '좋아요'API 호출 시 예상되는 문제
게시글의 '좋아요'수 등은 DB에 잦은 insert/delete문을 유발하여 성능에 영향을 줄 수 있습니다. 
이를 해결하기 위하여 사용자가 '좋아요'를 하거나 취소한 내역을 Redis에 임시저장한 후 주기적으로 DB에 적용하도록 구현하였습니다.

### Redis Pub/Sub으로 구현한 채팅



### ERD
https://www.erdcloud.com/d/WdiQSSJiKy4n6YFQr

