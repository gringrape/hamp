# 2019-09-15 

## hamp application backend

## 1. meeting api - crud 구현

restful api 의 meeting end point 에서 다음과 같은 crud 기능을 수행할 수 있도록 구현하였습니다.

-   meeting 상세
-   meeting 수정
-   meeting 삭제
-   meeting 입력

상세는 layered architecture 개념을 이용하였습니다. 비슷한 관심사를 가진 객체들을 하나의 레이어에 묶고 다른 레이어와 명확히 구별되게 하였습니다.

 

-   UI layer - controller
-   Application layer - service
-   Domain layer - entity, repository 

## 2. meeting api 예외 처리

사용자가 입력값을 잘못입력한 상황이나 기타 장애 상황을 다룰 수 있도록 예외처리를 해 주었습니다. 예외처리한 항목들은 다음과 같습니다.

-   meeting 입력시 title, description, topic 중 어느하나가 비어있으면 badRequest 응답
-   meeting 수정시 수정대상의 meetingId가 존재하지 않는 경우 notFound 응답
-   meeting 수정시 title, description, topic 중 어느하나가 비어있으면 badRequest 응답
-   meeting 삭제시 삭제 대상의 meetingId가 존재하지 않는 경우 

# 2019-09-16 

## 1. 테스트 항목 계획 개선

-   테스트 대상 케이스에서 의존하는 대상 클래스(이하 의존성)를 목으로 만든다
-   의존성에서 올바른 것을 호출하는지 확인한다.
-   **의존성에서 전달한결과를 ****이 객체가**** 올바르게 돌려주고 있는지 확인한다. **

## 2. 프론트엔드 구현 - meeting list

2-1. 목표 1. 네 개의 글을 담은 글의 목록을 화면에 렌더링하기.

-   세부 목표 1. 글 목록의 데이터를 얻어온다.
-   세부 목표 2. 얻어온 데이터를 렌더링 한다.

2-1-1.세부목표1. 글 목록의 데이터를 얻어오기.

-   hamp backend server에 http 요청을 보낼 수 있는 툴이 필요하다. 
-   axios를 사용해서 데이터를 얻어온다. 
    -   [x] 얻어올 시험 용 데이터를 입력한다. 
    -   [x] axios 를 통해서 데이터를 얻어와서 console에 띄워본다. 
-   Vue axios 이용하기
    -   <https://www.npmjs.com/package/vue-axios>
-   세부목표2. 글 목록의 데이터를 화면에 띄워본
    -   axios get의 response에 들어있는 data를 component data에 저장한다.
    -   v-for를 활용해서 반복문으로 띄워준다.
    -   문제발생.  길이가 들쭉날쭉하다 -> 고정시켜야 한다.
    -   문제발생2. v-for가 동작은 하지만 넣어준 태그에 빨간줄이 가있다. 어떻게 고쳐야할지 방법을 찾아야한다.
-   발생한 문제들
    -   문제1. axios를 설치한 후에 import axios from 'axios'를 어디서 해줄 것인지? 자바처럼 이걸 사용하는 모든 컴포넌트에서 개별적으로 설정해줘야하는건지 아니면, 통합적으로 설정해주는 방법이 있는지.
    -   문제2. crossOrigin 문제. 다른 어플리케이션에서 api에 접근할 수 없도록 하는 block에 걸려서 요청이 거부되었다. -> @CrossOrigin annotation을 붙여서 해결해 주었다.
    -   적용된 사진들

![](https://storage.googleapis.com/slite-api-files-production/files/6fcf9696-0cd1-4865-9098-64e9204d7bbc/image.png)

![](https://storage.googleapis.com/slite-api-files-production/files/c49f5988-43d7-4fb0-acf5-8b7af3f2643c/image.png)

## 3. 프론트엔드 구현 - meeting detail 

-   페이지를 바꾸는 방법 연구
    -   라우터에서 다른 component를 삽입하기
-   세부목표1. 모임 목록에서 버튼을 누르면 상세보기로 이동하도록 구현하기.
    -   모임 목록에서 상세보기 버튼을 누르면 실행되는 이벤트 함수를 구현하기.
    -   모임의 아이디를 전달할 방법을 찾기.
        -   <https://stackoverflow.com/questions/45296505/how-to-use-vue-router-params> -> 라우터를 사용해서 다른 컴포넌트에 값 전달하기
        -   v-for 를 사용한 반복 태그에서 아이디를 함수의 argument에 넣어서 전달하기 -> 성공
-   라우터를 사용해서 다른 컴포넌트에 parameter 전달하기
    -   1\. index.js 라우터 설정을 한다. -> path뒤에 :varName 을 추가한다.
    -   2\.  $router 이용해서 다른 컴포넌트를 푸쉬해준다.
    -   3\. 다른 컴포넌트에서 this.$route.params 를 사용해서 parameter에 접근한다.

![](https://storage.googleapis.com/slite-api-files-production/files/4c67e7d8-4ddd-4b43-839a-328610810689/image.png)

## 3. 프론트엔드 구현하기 #3** 모임의 삭제**

-   작업 순서
    -   axios를 사용해서 api에 삭제 요청 보내기
    -   리스트로 돌아가기
-   문제 발생
    -   삭제한 모임이 리스트에서 없어지지 않는 문제 발생 -> axios.get(모임목록주소) 를 다시 실행시켜주어야 한다
    -   라우터를 리프레쉬 해주는 것으로 해결
        -   <https://stackoverflow.com/questions/41301099/do-we-have-router-reload-in-vue-router>

![](https://storage.googleapis.com/slite-api-files-production/files/a3a997d4-58ec-4d46-a35f-67d5bd2caaac/image.png)

## 4. 프론트엔드 구현하기** #4 모임의 상세 part2**

-   디자인 구현하기
    -   <https://www.meetup.com/ko-KR/Free-Leaning-Korean-Language-with-Native-Korean/events/qqrrrqyzmbwb/> -> 참고 페이
-   실수의 발견
    -   meetDate에 모임이 열리는 날을 저장해야 하는데, 글을 작성한 시각을 저장해 버렸다. 후에 수정이 필요하다.
-   문제 발생과 해결
    -   1\. 텍스트와 버튼을 중앙에 오게 하고 싶다.
        -   mx-auto, width 조절
    -   2\. tailwindcss에서는 width를 제한해놓았고 이걸 customizing해주기가 귀찮다 -> width 를 percentage로 조절하는 것 사용하자.

# 2019/9/18

-   테스트 코드 수정
    -   테스트 객체가 올바른 값을 돌려주는지 확인
    -   테스트 객체가 의존하는 객체를 올바르게 호출하는지 확인

# 2019/9/19

## 1. 데이터 영속화

-   어플리케이션 데이터를 memory 에서 파일에 저장하도록 변경

## 2. 리프레쉬 수정

-   문제의 원인을 잘못파악했다.
    -   데이터를 가져오는 함수가 실행될때마다, "실행되었어요!"를 콘솔에 표시하도록 설정
    -   라우터에 컴포넌트가 마운트 될때 마다, mount 에 지정된 함수가 실행되는 것을 알 수 있음.
    -   즉, 라우터에 컴포넌트가 리스트로 바뀔때마다 데이터를 rest api로부터 받아오고, 렌더링이 일어난다 -> **라우터의 문제가 아니다.**
-   문제의 진짜 원인
    -   **delete 요청을 한 후에 순간적으로 get 요청이 일어나서 거의 동시에 요청을 하게 된다. 그결과 get 요청은 삭제되지 않은 목록을 받아오게 된다.**
-   문제의 원인을 잘못파악한 또 한가지 이유 - component의 mount가 실행되는 시점.
    -   <https://medium.com/witinweb/vue-js-%EB%9D%BC%EC%9D%B4%ED%94%84%EC%82%AC%EC%9D%B4%ED%81%B4-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0-7780cdd97dd4>
    -   라우터에 의해 다른 컴포넌트를 넣는다는 것은 이미 다시 그린다는 의미 이다. 따라서 그리기 직전에 실행되는 mount() 가 실행되지 않을리 없다. 
-   문제의 해결
    -   업데이트가 일어나고 데이터를 받아 올 수 있도록 기다렸다가 받아오도록 한다.

![](https://storage.googleapis.com/slite-api-files-production/files/a2f4212e-b65e-4c03-af3a-7efa95470269/image.png)

-   하지만.... 이것으로 충분한가?
    -   삭제가 진행 중 일때, 해당 point 에 대한 get 요청을 처리해서 준다는 것이 과연 옳은 일일까? 데이터에 접근하는 것을 잠깐 막아야 하지 않을까?

# 2019-09-23

##  1. 목표 작업

-   [x] 지역, 토픽을 기준으로 한 필터링 기능 - B
-   [ ] 지역, 토픽을 기준으로 한 필터링 기능 - F
-   [ ] 목록 리스트 페이징- F

## 2. 지역, 토픽을 기준으로 한 필터링 기능

-   개요
    -   지역과 토픽을 기준으로 검색을 하여 검색한 목록을 띄워줍니다.

**&lt;처리할 http 요청의 모양>**

    http get localhost:8080?address=서울&topic=1

-   백엔드 작업
    -   지역 필터링
    -   토픽 필터링
-   프론트엔드 작업
    -   카테고리를 선택 할 수 있는 컴포넌트 제작.
    -   카테고리와 지역을 선택할 수 있는 컴포넌트 제작. 

## 2-1. 지역 필터링 구현- B(완료)

-   [x] Meeting entity에 address property 추
-   [x] controller 구현
    -   (요청) get, url="/meetings?address=\*\*\*"
    -   status 200
    -   service 호출
    -   호출 결과 반환 
-   [x] service 구현
    -   (서비스 호출) getMeetings(address)
    -   repository 호출
    -   호출 결과 반환

## 2-2.토픽 필터링 구현 -B(완료)

-   [x] 토픽 엔터티 추가
    -   생성 테스트
-   [x] 토픽 관리 기능 추가
    -   [x] 목록 얻기
    -   [x] 입력 하기
-   [x] 검색 기능 추가
    -   [x] controller 구현
    -   [x] service 구현

# 2019-09-24 

## 1. 목표 작업

-   [x] 인증 관련 내용 학습 및 정리
    -   http 기본 인증
    -   다이제스트 인증 -> (추후 학습)
    -   OAuth 프로토콜 -> (추후 학습)
    -   세션과 토큰, 인증
-   [x] 로그인 작업 - 사용자 관리 - B

## 2. 인증 관련 내용 학습 & 정리

**2-1. http 기본 인증(RFC 2617)**

-   기본 인증 과정
    -   **Client **- 사용자의 리소스 요청 
    -   **Server **- 401 Authorization required 응답을 반환
    -   **Client(브라우져)** - 사용자 이름과 비밀번호를 입력하는 창 띄움.
    -   **Client(브라우져)**
        -   사용자의 이름과 비밀번호를 콜론으로 이어 붙임
        -   위 결과를 **base-64 **방식으로 인코딩
        -   Authorization 헤더에 값을 담아 서버로 전송
    -   **Server -** Authorization 헤더의 값을 디코딩, 검사 후 문제가 없으면 200 응답을 반환
-   기본 인증 과정의 문제점
    -   base64 인코딩은 쉽게 디코딩이 가능하다 -> 사용자의 기본정보가 노출된다.
-   기본 인증의 대안(강력한 암호화)
    -   다이제스트 인증 -> 2014년 무렵부터는 거의 사용되고 있지 않다고 함.
    -   **SSL을 이용한 암호화된 http 사용**
    -   **OAuth(rfc6749)  **

**2-2. 세션, 토큰**

**2-2-1. http 요청, 응답의 무상태성과 세션**

지난 기록에 대한 상태를 저장하지 않는다. 

**->** 고유한 사용자 경험이 필요하다

**-> **연결의 지속(session) 필요.

**-> **session을 구현하기 위해서는 매 요청마다 고유한 아이디(session id)의 확인 작업이 필요하다.

**2-2-2. httpSession 객체(sessionId를 통해 접근 가능)를 통한 인증**

-   httpSession 객체(이하 세션 객체)에 사용자 정보를 저장하는 방식의 문제점
    -   세션 아이디 탈취가 일어날 경우 사용자 정보가 그대로 넘어가게 된다.
-   세션 객체에 암호화된 토큰을 저장(ActiveToken 방식)
    -   토큰을 확인(인증)을 통해서 사용자 정보 접근권한 부여 
-   암호화된 토큰에 사용자 정보 입력
    -   토큰을 해석해서 사용자 정보를 얻고 활용

**&lt;세션>**

![](https://storage.googleapis.com/slite-api-files-production/files/b5c13d4e-d04d-45e9-b60c-3b54275d978c/image.png)

## **3. 로그인 작업 - B**

**3-1. 사용자 관리(완료)**

-   개요
    -   사용자 엔드 포인트를 설정하여 기본적인 crud를 통해서 데이터를 주고 받을 수 있도록 설정.
    -   사용자 Entity
        -   이메일 - identifier, 사용자를 고유하게 식별이 가능하도록 하는 역할
        -   닉네임 - identifier를 노출시키고 싶지 않을때 사용 가능한 것. 
        -   level - 숫자의 값으로 해당 사용자가 가진 권한의 크기와 종류를 나타냄.
            -   level1 - 일반 유저
            -   level2 - 관리자
-   crud 구현
    -   [x] list -> getUsers
    -   [x] create -> addUser
    -   [x] update -> modifyUser
    -   [x] delete -> deactivateUser
-   crud 구현 - 문제해결
    -   http error 400 - bad request - 이 응답을 받은 경우 요청부분을 확인하고 올바르게 바꾸어 주어야 한다. -> 개선점. 문제 상황을 정확하게 이해하려는 노력이 아직 부족하다. 

 

**3-2. 회원가입**

**3-3. 인증**

**3-4. Jwt**

# 2019-09-27 

## 1. 목표 작업

-   [x] 필터링 기능 코드 수정 - B
-   [ ] 회원 가입 기능 구현 - B
-   [ ] 목록 페이징 처리 - F 

## 2. 필터링 기능 코드 수정

**2-1. 개요**

 주소, 토픽아이디 파라메터가 전달되지 않은 경우에 대한 처리. method를 동적인 상황에 맞게 호출하는 방식 혹은 파라메터의 동적인 값에 따라 호출을 다르게 하는 방법이 고안되어야 함.

**2-2. 해결**

-   방식1 - Optional 활용
-   방식2 - request parameter의 default value 활용

**&lt;Controller>**

![](https://storage.googleapis.com/slite-api-files-production/files/ce330fac-2aa0-4add-8a3c-5606d0e13513/image.png)

**&lt;Service>**

![](https://storage.googleapis.com/slite-api-files-production/files/5009a57e-bbfe-4564-b2b0-d85dfd17b0a1/image.png)

# 2019-10-06 

## 1. 계획

-   [x] 기능 아웃라인 설정

## 2. 기능 아웃라인 설정

-   **모임 목록** 
    -   전체 목록
    -   주제별 필터링
    -   지역별 필터링
    -   날짜별 필터링
    -   **(부가)** 목록 페이징 제공 
-   **모임 생성**
    -   모임 기본 정보 입력
    -   모임 지역 설정 -> location picker 활용
    -   모임 일자 설정 -> date picker 활용
-   **모임 상세보기 **
    -   모임 댓글
    -   모임 기본 정보
    -   모임 참여자의 아이디 목록
-   **회원 가입, 로그인 **
    -   회원가입 폼
    -   로그인 폼
-   **회원 정보 보기**
    -   회원 기본 정보
    -   회원 정보 수정
    -   참여하는 모임 목록  
-   추가적인 기능
    -   모임 참여시 원하는 경우 메일 발송
    -   회원간 쪽지 기능

## 3. 구현 상태 파악을 위한 표 그리기

|                                                                                                 |       **Frontend ****😀**** **       | **Backend ****😁**** ** |
| :---------------------------------------------------------------------------------------------- | :----------------------------------: | :---------------------: |
| **모임 목록**                                                                                       |                                      |                         |
|   - 전체 목록                                                                                       |      모두 보기 링크 클릭시 종축으로 정렬되도록 구현      |           🙂            |
|   - [주제별 필터링](https://gringrape200.slite.com/app/channels/jbddsvzzjj/notes/ihpsd80agg#4035c186) | **실패** -> image binding 에서 암호화 이슈 발생 |           🙂            |
|   - 지역별 필터링                                                                                     |                                      |           🙂            |
|   - 제목별 필터링                                                                                     |                                      |                         |
|   - 날짜별 필터링                                                                                     |                                      |                         |
| **모임 생성**                                                                                       |                                      |                         |
|   - 모임 기본 정보                                                                                    |                                      |           🙂            |
|   - 모임 지역 설정          (Location Picker)                                                         |                                      |            X            |
|   - 모임 날짜 설정  (Date Picker)                                                                     |                                      |            X            |
| **모임 상세 보기**                                                                                    |                                      |                         |
|   - 모임 기본 정보                                                                                    |                  🙂                  |            🙂           |
|   - 모임 참여자의   아이디 목록                                                                            |                                      |                         |
| **회원가입, 로그인**                                                                                   |                                      |                         |
|   - 회원 가입                                                                                       |     🙂 - **추가작업:** 포탈 아이디를 통한 로그인    |            🙂           |
|   - 로그인                                                                                         |                                      |            🙂           |
| **회원 정보 보기**                                                                                    |                                      |                         |
|   - 회원 기본 정보                                                                                    |                                      |                         |
|   - 회원 정보 수정                                                                                    |                                      |                         |
|   - 참여하는 모임   목록                                                                                |                                      |                         |
| **추가 기능**                                                                                       |                                      |                         |
|   - 모임 참여시 메일 발송                                                                                |                                      |                         |
|   - 회원간 쪽지 기능                                                                                   |                                      |                         |
|   - 반응형 설계                                                                                      |                                      |                         |

## 3. 주제별 필터링 프론트 구현 

-   meet-up 페이지 참

![](https://storage.googleapis.com/slite-api-files-production/files/b98e419b-3b0b-4e2a-a1c4-dfe3e1b3f0ce/image.png)

-   구현 내용
    -   백엔드 데이터 형식 참고(Topic) - id, name.
    -   topicId 에 따른 이미지 주소를 데이터로 저장. 
    -   category mount 시에 목록을 가져오도록 설정. 
    -   카테고리 타이틀 렌더링
    -   카테고리 이미지 링크 렌더링
-   문제 발생
    -   1\. 라우터로 설정된 view 가 동작하지 않는다.
    -   2\. get topics 의 요청시에 접근이 거부 
-   문제 해결
    -   1\. 라우터 경로 설정에서 category와 list 컴포넌트를 등록하고 옳게 가져다 쓰기
    -   2\. cors 를 통해서 cross origin request를에 처리할 수 있도록 설정한다. 

**&lt;cross origin annotation 설명>**

![](https://storage.googleapis.com/slite-api-files-production/files/63406620-acfa-47cc-a35e-1e5a1e60659b/image.png)

# 2019-10-07

##  1. 현실적인 어플리케이션 개발 계획

-   개발 우선 순위 
    -   [x] 회원가입 - 프론트엔드
    -   [ ] 로그인
        -   [x] 로그인 api
        -   [ ] 로그인 폼
    -   [ ] 모임 생성
    -   [ ] 모임 검색

## 2. 회원 가입 폼 프론트엔드 개발

2.1. 발생한 문제점 정리

-   router 를 통한 signUpForm 컴포넌트로의 링크 문제
    -   &lt;a> 태그를 &lt;router-link> 태그로 바꾸어서 해결
-    요소들을 가운데로 정렬
    -   text-center

2.2. 추가 과제 정리

-   포털 아이디를 통한 로그인
-   회원 위치 정보의 추가

## 3. 모임전 반드시 해야 할 이론 공부

-   자바 스크립트를 통한 올바른 비동기 처리 방법

# 2019-10-09

## 1. 계획

-   [x] 토큰을 이용한 세션유지 방법 강의
-   [x] 토큰을 이용한 세션유지 구현 - B
-   [x] 로그인 폼 - F
-   [x] 토큰 전달 로직 - B
-   [ ] 로그인시 토큰 전달 - F

## 2. 로그인 폼 만들기

-   **개요**
    -   이메일과 비밀번호를 입력받아 전송
    -   응답에 따라 토큰을 받아 저장
-   **사용자 정보를 입력, 전송**
    -   loginForm 컴포넌트 생성
    -   이메일 비밀번호 입력 창 구현
    -   전송 로직 구현
-   **토큰 저장**
    -   토큰을 저장하는 방식의 문제

## 3. 토큰을 저장하는 방식

3-1. 쿠키 vs webstorage

-   **참고자료**
    -   <http://lazyhoneyant.blogspot.com/2016/08/jwt.html>
-   **참고자료 정리**
    -   webStorage는 자바스크립트로 접근 가능 -> XSS 공격에 취약
    -   cookie는 csrf 공격에 취약 -> **추가공부**

3-2. 토큰을 쿠키에 저장하여 전

-   **참고자료**
    -   <https://attacomsian.com/blog/cookies-spring-boot>
