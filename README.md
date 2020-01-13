# 주차장 조회 애플리케이션

## To-Do list

* API 애플리케이션
  * [x] 프로젝트 구성
  * [ ] OpenAPI 컨슈머 기능 구현
    * [x] 전체 데이터 조회
    * [ ] 주차장 코드로 조회 (데이터 최신화)
  * [ ] 주차장 정보 DB 저장
    * [ ] 전체 데이터 저장 (Refresh)
      * [ ] Quartz로 분 단위 최신화
* Front 애플리케이션
  * [x] 프로젝트 구성
  * [ ] UI 구성
  * [ ] API 연계
  * [ ] 정렬 기능 구현
    * [ ] 최소비용
    * [ ] 최단거리
* Proxy 애플리케이션
  * [x] 프록시 구성
  
## 기본사항

* 서버 포트
  * 프록시: 8000 포트를 사용합니다.
  * API: 8080 포트를 사용합니다.
  * Front: 8001 포트를 사용합니다.

* 웹사이트 엔트리: http://localhost:8000/front

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

```console
$ ./gradlew bootJar
$ docker build . --tag parkinglot:0.1
$ docker run -p 8080:8080 parkinglot:0.1
```

### API 문서 생성

터미널에서 아래 명령을 실행, 혹은 IDE에서 `gradle - Tasks - documentation - asciidoctor` 명령을 실행하고 `{rootProject}/parkinglot-api/build/asciidoc/html5/index.html` 을 확인합니다.

```console
$ ./gradlew parkinglot-api:asciidoctor
```
