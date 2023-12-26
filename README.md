## 개요
뱅크셀러드와 같은 사용자들이 예산을 설정하여 관리하고 지출을 기록하면서 돈을 아끼는데에 도움을 주는 애플리케이션입니다.

## 환경
Spring Boot, Gradle, Mysql, Spring Data JPA, Querydsl

## ERD
<img width="346" alt="스크린샷 2023-12-26 오전 11 05 08" src="https://github.com/nswon/budget-management/assets/80371249/61493d54-6319-4b81-8a0e-ae0dcb641749">

## 주요 관심사
#### 아키텍쳐
- 프레젠테이션 레이어, 비즈니스 레이어, 구현 레이어, 데이터 접근 레이어 총 4가지의 레이어로 구성되어 있습니다.
- 레이어간 경계를 침범하지 않고 순방향으로만 흐르도록 했습니다.
- 구현 레이어에서 비즈니스 로직을 구현함으로써 비즈니스 레이어에서는 비즈니스 흐름을 이해하기가 쉬워집니다. 
- 객체 간 책임과 협력을 고민하는 객체지향적 프로그래밍을 했습니다.

#### 성능 개선
- 애플리케이션단에서 비즈니스 로직을 처리하기보다 쿼리부분에서 처리할 수 있는 부분을 찾아보면서 적용하였습니다.
- 쿼리 프로시저로 대량의 데이터를 넣고 속도를 테스트하였습니다.
- 인덱스를 적용하고 최적화시켜서 성능을 개선시켰습니다.

#### 테스트 코드
- 중복되거나 필요 없는 테스트 코드가 뭔지 고민하고 있습니다.
- 코드를 넘어 해당 기능의 중요도와 상태를 체크하여 적절하게 테스트를 실행할 예정입니다.

