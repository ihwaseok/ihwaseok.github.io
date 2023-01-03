---
title: AOP 지원
updated: 2022-12-11 08:59:24Z
created: 2022-12-08 06:14:41Z
latitude: 37.26357270
longitude: 127.02860090
altitude: 0.0000
---

## Aspect Oriented Programming (관점 지향 프로그래밍)
- 어떤 로직을 기준으로 핵심적인 관점, 부가적인 관점으로 나누어서 보고 그 관점을 기준으로 각각 모듈화 하는 것
→ 흩어진 관심사를 관점 기준으로 모듈화하고 핵심적인 비즈니스 로직에서 분리하여 재사용하겠다는 것
ex) 핵심관점 	= 비즈니스 로직
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;부가적관점 	= DB연결, 로깅, 입출력
<br>

## AOP 주요개념
- Aspect = 흩어진 관심사를 모듈화 한 것. 주로 부가기능을 모듈화함
- Target = Aspect를 적용하는 곳 (클래스, 메서드 .. )
- Advice = 실질적으로 어떤 일을 해야할 지에 대한 것
&emsp;&emsp;&emsp;&nbsp;&nbsp;&nbsp;&nbsp;실질적인 부가 기능을 담은 구현체
- JointPoint = Advice가 적용될 위치
 &emsp;&emsp;&emsp;&emsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;끼어들 수 있는 지점. 메서드 진입 지점, 생성자 호출 시점
&emsp;&emsp;&emsp;&emsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;필드에서 값을 꺼내올 때 등 다양한 시점에 적용가능
- PointCut = JointPoint의 상세한 스펙을 정의한 것. 
&emsp;&emsp;&emsp;&emsp;&nbsp;&nbsp;&nbsp;&nbsp;'A란 메서드의 진입 시점에 호출할 것'과 같이 더욱 구체적으로 Advice가 실행될 지점을 결정
<br>

<img src="../../../_resources/933ce60e444acf74c1e6adb8248bfa0e.png" width="500"/>