# Hamp - 함께 프로그래밍
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fgringrape%2Fhamp.svg?type=shield)](https://app.fossa.io/projects/git%2Bgithub.com%2Fgringrape%2Fhamp?ref=badge_shield)


## *. 개발 일지, 웹페이지 url

개발일지 #1 - https://slite.com/api/s/note/YBGBjiZ8kfXX5eE5NMHLVp/Hamp

개발일지 #2 - https://slite.com/api/s/note/7Ab5t7FMpDpiBkkBB5PHAU/Hamp-2

url - http://hamp.xyz.s3-website.ap-northeast-2.amazonaws.com/

## 1. 주제 
- 모여서 함께 **개발 공부**를 할 수 있도록 **중개**하는 사이트.

## 2. 프로젝트를 통한 공부 목표
- rest api 개발
- 단위테스트 작성
- vue.js 통한 프론트엔드 데이터 처리
- tailwindcss 를 통한 화면 구현
- AWS 배포
- 프론트 단과 백단의 분리 개발 및 배포

## 3. 최소 요구사항 분석 - 사용자 스토리
- 해결하고자 하는 문제
	- 함께 공부하고 싶어하는 사람들을 **편리하게** 연결해준다.  

- 사용자의 분류
	- 주최자
	- 참가자
	- (관리자)

- 사용자 스토리 (추가 예정)
	- 형식
		- (누구)는 (무엇)을 위해 (무엇)을 할 수 있다.  
	- 주최자
		- 주최자는 모임에 참가할 사용자를 모으기 위해 모임 정보를 작성하고 등록 할 수 있다.
		- 주최자는 모집을 끝내기 위해 모임 정보를 삭제 할 수 있다.
		- 주최자는 모임의 정보를 변경하기 위해 등록된 모임 정보를 수정할 수 있다.
	- 참가자
		- 참가자는 참여가능한 모임을 알아보기 위해 모임의 목록을 확인 할 수 있다. 
		- 참가자는 모임에 실제로 참여하기 위해서 모임의 상세 정보를 확인 할 수 있다.  	 

## 4. 개발 목표 prototype
- 최소한의 요구조건을 수행할 수 있는 프로토타입 개발

- 최소한의 기능 목록
	- 모임 정보 
		- 모임의 정보를 생성, 수정, 삭제 할 수 있는 기능을 가진 REST api  서버의 endpoint
		- REST api 의 테스트 자동화. 
		- 모임의 정보를 영속화 -> 데이터 베이스 사용.
	- 회원정보, 로그인
		- 회원데이터 영속화
		- 특정 유저에게 고유한 경험(개최한 모임 정보 수정, 삭제)을 제공하기 위해 로그인 기능
	- 화면
		- 모임의 목록을 표시하는 페이지
		- 모임의 상세정보를 표시하는 페이지
		- 모임을 등록하기 위해 모임 정보를 작성하는 창
		- 회원 정보를 입력하는 창
		- 로그인 창       

## 5. 프로젝트 진행 계획
- 프로토 타입 개발 -> 배포 -> 요구사항 추가 -> 개발 목표 추가 -> 다음 버젼 개발 -> 배포 -> ...

## License
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fgringrape%2Fhamp.svg?type=large)](https://app.fossa.io/projects/git%2Bgithub.com%2Fgringrape%2Fhamp?ref=badge_large)