---
title: MyBatis-Spring
updated: 2022-12-14 07:54:01Z
created: 2022-12-14 07:27:25Z
latitude: 37.26357270
longitude: 127.02860090
altitude: 0.0000
---

## MyBatis-Spring
-  MyBatis를 Spring Framework에 녹여내 좀 더 쉽게 사용하고자 하는 연동 모듈
<br>

## 구조
<img src="../../../_resources/69ac0088ed8ef76107e616df08642238.png" width="800"/>
<blockquote>
<b>🔶 프로그램 처음 시작시 프로세스</b><br>
1. SqlSessionFactory 빌드 요청<br>
2. Factory 생성을 위하여 설정 파일 조회<br>
3. 설정 파일을 기반으로 Factory 생성<br>
4. SqlSessionTemplate 및 Mapper Interface의 <abbr title="객체를 감싸서 해당 객체의 작업을 가로채는 객체">프록시 객체</abbr> 생성<br><br>
<b>🔶 클라이언트 요청에 대한 프로세스</b><br>
5. 클라이언트 요청<br>
6. 프로그램이 Mapper Interface를 호출<br>
7. Mapper Interface를 구현한 객체가 호출한 메소드에 해당하는 SqlSessionTemplate메소드를 호출<br>
8. 프록시 사용 및 안전한 SqlSession 메소드를 호출<br>
9. Factory를 호출하여 SqlSession 생성<br>
10. SqlSession 반환<br>
11. Mappgin File에서 실행할 SQL문을 가져와서 SQL문 실행
</blockquote>
<br>

## SqlSessionFactoryBean
- MyBatis 구성 파일에 정의 된 정보를 기반으로 SqlSessionFactory Spring DI 컨테이너에 객체를 빌드 하고 저장 하는 컴포넌트
<br>

## MapperFactoryBean
- 싱글톤 Mapper 객체를 빌드하고 Spring DI 컨테이너에 객체를 저장하는 컴포넌트
<br>

## SqlSessionTemplate
- 마이바티스 스프링 연동모듈의 핵심
- 필요한 시점에 세션을 닫고, 커밋하거나 롤백하는 것을 포함한 세션의 생명주기를 관리
- 마이바티스 예외를 스프링의 DataAccessException로 변환하는 작업을 처리
- 스프링 트랜잭션의 일부처럼 사용될 수 있고 여러개 주입된 매퍼 클래스에 의해 사용되도록 쓰레드에 안전함