# 주차장 조회 애플리케이션

## To-Do list

* API 애플리케이션
  * [x] 프로젝트 구성
  * [x] OpenAPI 컨슈머 기능 구현
    * [x] 조건으로 데이터 조회: 주소
    * [x] 서버사이드 조건필터 적용: 주차장 명, 전화번호, 페이징
    * [x] 정렬 기능 구현
      * [x] 최소비용
      * [x] 최단거리
  * [ ] ~~주차장 정보 DB 저장~~
    * [ ] ~~전체 데이터 저장 (Refresh)~~
      * [ ] ~~Quartz로 분 단위 최신화~~
* Front 애플리케이션
  * [x] 프로젝트 구성
  * [x] UI 구성
  * [x] API 연계
  * [x] Geolocation API 적용
* Proxy 애플리케이션
  * [x] 프록시 구성

### 요구사항

* 검색조건
  * [x] 주소: e.g. 마포구, 망원동
  * [x] 전화번호
  * [x] 주차장 명
  * [x] 현재위치 가까운 순으로 검색 +
* 조회결과
  * [x] 페이징
  * [x] 예상요금 최소
  * [x] 현재 주차가능여부 표시
  
## 기본사항

* 서버 포트
  * 프록시: 80 포트를 사용합니다.
  * API: 8080 포트를 사용합니다.
  * Front: 8001 포트를 사용합니다.

* 웹사이트 엔트리: http://localhost:80/front/

## 사용방법

### 애플리케이션 실행

터미널에서 아래 명령을 실행합니다.

```console
$ ./gradlew parkinglot-front:bootRun # 프론트 애플리케이션 실행
$ ./gradlew parkinglot-api:bootRun   # API 애플리케이션 실행
$ ./gradlew proxy:bootRun            # 프록시 애플리케이션 실행
```

### 도커 이미지로 실행

도커 이미지는 그래들 빌드 (`gradle - bootJar`) 후에 생성된 jar 파일을 기반으로 생성할 수 있습니다.

#### 도커 이미지 빌드

```console
$ ./gradlew bootJar # bootJar 생성

$ docker build ./parkinglot-front --tag park-front:0.1 # 프론트 애플리케이션 이미지 빌드
$ docker build ./parkinglot-api --tag park-api:0.1 # API 애플리케이션 이미지 빌드
$ docker build ./proxy --tag park-proxy:0.1 # Proxy 애플리케이션 이미지 빌드
$ docker run -p 8080:8080 park-api:0.1 
$ docker run -p 8001:8001 park-front:0.1 
$ docker run -p 80:80 park-proxy:0.1 
```

##### 도커 컴포즈를 통한 애플리케이션 실행 (WIP)

도커 컴포즈 명령으로 애플리케이션을 실행한다.

```console
$ docker-compose up
```


### API 문서 생성

터미널에서 아래 명령을 실행, 혹은 IDE에서 `gradle - Tasks - documentation - asciidoctor` 명령을 실행하고 `{rootProject}/parkinglot-api/build/asciidoc/html5/index.html` 을 확인합니다.

```console
$ ./gradlew parkinglot-api:asciidoctor
```
