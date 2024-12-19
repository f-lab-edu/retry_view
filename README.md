# Retry View
제품에 대한 리뷰를 작성하고, 중고 물품 거래시 해당 물품의 리뷰도 확인할 수 있으며 사용자나 관리자끼리 실시간으로 메시지를 주고받으며 대화할 수 있는 서비스 API 개발 프로젝트입니다.

### 기간
2024.09 ~ (진행중)

### 기술
Java, SpringBoot, JPA, Redis, MySQL, Docker, Jenkins, AWS

### Swagger 주소
[(링크)](http://ec2-15-164-72-162.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui/index.html)

### ERD
https://www.erdcloud.com/d/WdiQSSJiKy4n6YFQr
  

![RetryView drawio](https://github.com/user-attachments/assets/fae0fc9c-eedd-4855-8991-66b4b678046b)

# ✨ OAuth2를 이용한 SNS로그인
일반적인 회원가입 외에도 Spring Security, OAith2를 이용하여 구글, 네이버 계정을 연동하여 회원가입/로그인을 할 수 있도록 구현하였습니다. 
사용자의 권한 정보는 UserDetails와 OAuth2User를 한 클래스에 구현하여 한 곳에서 관리되도록 하였고, 해당 기능을 작업하며 OAuth 2.0의 동작 방식에 대해서 배울 수 있었습니다.

# ✨ 잦은 '좋아요'API 호출 시 예상되는 문제
게시글의 '좋아요'수 등은 DB에 잦은 insert/delete문을 유발하여 성능에 영향을 줄 수 있다고 생각했습니다. 
이를 해결하기 위하여 사용자가 '좋아요'를 하거나 취소한 내역을 Redis에 임시저장한 후 주기적으로 DB에 적용하도록 구현하였습니다. Redis를 사용한 이유는 인메모리 방식으로 데이터 접근 및 제어 처리 속도가 빠르기 때문에 다른 데이터에 비해 조회, 추가, 삭제가 잦은 좋아요기능에 사용하면 좋을 것이라 생각되어 사용하였습니다.

# ✨ Redis Pub/Sub으로 구현한 채팅
위의 기능을 작업하며 Redis의 Publish, Subscribe를 이용하여 메시지 큐처럼 사용할 수 있다는 것을 알게되었고, 이를 이용하여 채팅 기능을 구현하였습니다.
작업하며 Redis를 DB로만 사용하는 것이 아닌 메시지 서버로도 사용할 수 있어 편리하지만, Redis의 특성상 데이터가 유실될 수 있기에 유실되어도 괜찮은 알림 등에만 사용하거나 실서비스에는 다른 방식으로 구현해야겠다는 생각이 들었습니다.


### ERD
https://www.erdcloud.com/d/WdiQSSJiKy4n6YFQr

