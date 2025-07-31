// 루트 프로젝트 이름 설정
rootProject.name = "UserMngSystem"

// 공통 도메인 및 유틸을 포함하는 core 모듈
include("core")

// 사용자 인증 및 인가 처리를 담당하는 auth 모듈
include("auth")

// 카카오톡 메시지 전송 기능을 담당하는 kakao 모듈
include("kakao")

// SMS 메시지 전송 기능을 담당하는 sms 모듈
include("sms")