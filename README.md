# Retry View
제품에 대한 리뷰를 작성하고, 중고 물품 거래시 해당 물품의 리뷰도 확인할 수 있으며 사용자나 관리자끼리 실시간으로 메시지를 주고받으며 대화할 수 있는 서비스 API 개발 프로젝트입니다.

![RetryView (1)](https://github.com/user-attachments/assets/fc23d38e-8811-4530-aa01-7d8c0fe109b8)


### 기술
Java, SpringBoot, JPA, Redis, MySQL, Docker, Jenkins, AWS


### OAuth2를 이용한 SNS로그인
Spring Security, OAith2를 이용하여 구글, 네이버 계정을 연동하여 회원가입/로그인을 할 수 있도록 구현하였습니다.

### 잦은 데이터 업데이트 제한 
게시글의 '좋아요'수 등은 DB에 잦은 update문을 유발하여 성능에 영향을 줄 수 있습니다.

### Redis Pub/Sub으로 구현한 채팅


### ERD
https://www.erdcloud.com/d/WdiQSSJiKy4n6YFQr

