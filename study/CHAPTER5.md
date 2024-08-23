# CHAPTER 5: JUnit 5 기초

### JUnit 5 모듈 구성

- JUnit 플랫폼
- JUnit 주피터
- JUnit 빈티지

### @Test 애노테이션과 테스트 메서드

JUnit 코드의 기본 구조는 @Test 애노테이션을 메서드에 붙이는 것이다.

- @Test 애노테이션을 붙인 메서드는 private이면 안 된다.

JUnit Assertions 클래스는 assertEquals() 메서드와 같이 값을 검증하기 위한 다양한 정적 메서드를 제공한다.

### 주요 단언 메서드

- `assertEquals(expected, actual)`
- `assertNotEquals(unexpected, actual)`
- `assertSame(Object expected, actual)` : 두 객체가 동일한 객체인지 검사한다.
- `assertNotEquals(Object unexpected, actual)`
- `assertTrue(boolean condition)`
- `assertFalse(boolean condition)`
- `assertNull(Object actual)`
- `assertNotNull(Object actual)`
- `fail()` : 테스트 실패 처리

Assertions가 제공하는 익셉션 발생 유무 검사 메서드

- `assertThrows(Class<T> expectedType, Executable executable)`
- `seertDoesNotThrow( Executable executable)`

### 테스트 라이프 사이클

- `@BeforeEach`
- `@AfterEach`
- `@BeforeAll`
- `@AfterAll`

```sql
public class LifecycleTest {
	public LifecycleTest() {
		System.out.,println("new LifecycleTest");
	}

	@BeforeEach
	void setUp() {
		System.out.,println("setUp");
	}

	@Test
	void a() {
		System.out.,println("A");
	}
	@Test
	void b() {
		System.out.,println("B");
	}
	@BeforeEach
	void tearDown() {
		System.out.,println("tearDown");
	}
}
```

**실행 결과**

```sql
***new LifecycleTest***
setUp
A
tearDown
***new LifecycleTest***
setUp
B
tearDown
```

- **@Test 메서드를 실행할 때마다 객체를 새로 생성하는 점을 주의하자**

### 테스트 메서드 간 실행 순서 의존과 필드 공유하지 않기

각 테스트 메서드는 서로 독립적으로 동작해야한다.

서로 필드를 공유한다거나 실행 순서를 가정하고 테스트 작성하지 말아야한다.

JUnit은 테스트 메서드의 실행 순서를 지정하는 방법을 제공하고 있다, 하지만 각 테스트는 독립적으로 동작해야 한다**. 테스트 메서드 간에 의존이 생기면 유지보수를 어렵게 만든다.**

### 추가 애노테이션

- `@DisplayName`
- `@Disabled`