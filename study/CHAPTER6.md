# CHAPTER 6: 테스트 코드의 구성

## 상황

```
노련한 개발자는 어떤 상황이 실행 결과에 영향을 줄 수 있는지 찾기 위해 노력한다.
```

### 기능에서의 상황

주어진 상황에 따라 기능 실행 결과는 달라진다. 이는 테스트 코드 구조에도 영향을 준다.

## 테스트 코드의 구성 요소: 상황, 실행, 결과 확인

### **given, when, then**

어떤 상황이 주어지고, 그 상황에서 기능을 실행하고, 실행한 결과를 확인하는 세 가지가 테스트 코드의 기본 골격이다.

### JUnit

JUnit에서 상황을 설정하는 방법은 테스트할 대상에 따라 달라진다.

- **객체를 생성할 때 정답 숫자를 지정**하는 경우

    ```java
    @Test
    void exactMatch() {
    	// 정답이 456인 상황
    	BaseballGame game = new BaseballGame("456");
    	// 실행
    	Score score = game.guess("456)
    	// 결과 확인
    	assertEquals(3, score.strikes());
    	assertEquals(0, score.balls());
    }
    
    @Test
    void noMatch() {
    	// 정답이 123인 상황
    	BaseballGame game = new BaseballGame("123");
    	// 실행
    	Score score = game.guess("456)
    	// 결과 확인
    	assertEquals(0, score.strikes());
    	assertEquals(0, score.balls());
    }
    ```

- **@BeforeEach**를 적용한 메서드에서 상황을 설정할 경우

    ```java
    @BeforeEach
    void givenGame() {
    	BaseballGame game = new BaseballGame("456");
    }
    
    @Test
    void exactMatch() {	// 실행
    	Score score = game.guess("456)
    	// 결과 확인
    	assertEquals(3, score.strikes());
    	assertEquals(0, score.balls());
    }
    
    @Test
    void noMatch() {
    	// 실행
    	Score score = game.guess("456)
    	// 결과 확인
    	assertEquals(0, score.strikes());
    	assertEquals(0, score.balls());
    }
    ```

- **상황이 없는 경우**
  2장에서 사용한 암호 강도 측정의 경우 결과에 영향을 주는 상황이 존재하지 않으므로 기능을 실행하고 결과를 확인하는 코드만 포함할 수 있다.

    ```java
    @Test
    void meetsAllCriteria_Then_Strong() {	
    	// 실행
    	PasswordStrengthMeter meter = new PasswordStrengthMeter();
    	PasswordStrength result = meter.meter("ab12!@AB");
    	
    	// 결과 확인
    	assertEquals(PasswordStrength.STRONG, result);
    }
    ```

- **실행 결과**를 확인하는 방법으로 **리턴 값**을 사용하는 경우나 **익셉션**을 발생하는 방법이 있다.

    ```java
    @Test
    void genGame_With_DupBumber_Then_Fail() {	
    	assertThrows(IllegalArgumentException.calss,
    		() -> new BaseballGame("110")
    	);
    }
    ```


## 외부 상황과 외부 결과

상황 설정은 테스트 대상뿐만 아니라 외부 요인도 있다.

```java
File dataFile = new File("file.txt");
long sum = MathUtils.sum(dataFile);
```

파일이 존재하지 않는 상황을 생각해보자.

- 존재하지 않는 파일을 경로로 사용할 경우

```java
@Test
void noDataFile_Then_Exception() {
	File dataFile = new File("badpath.txt");
	assertThrows(TllegalArgumentException.class,
	() -> MathUtils.sum(dataFile));
}
```

- 명시적으로 파일이 없는 상황

```java
@Test
void noDataFile_Then_Exception() {
	givenNoFile("badpath.txt");
	File dataFile = new File("badpath.txt");
	assertThrows(TllegalArgumentException.class,
	() -> MathUtils.sum(dataFile));
}

private void givenNoFile(String path) {
	File file = new File(path);
	if (file.exists()) {
		boolean delete = file.delete();
		// 해당 경로에 파일이 존재하는지 검사해서 존재할 경우 해당 파일을 삭제한다.
		if (!deleted)
			throw new RuntimeException("faile givenNoFile:" + path);
	}
}
```

- 상황에 알맞는 파일을 미리 만들어 둔다.
  다른 개발자도 테스트를 실행할 수 있어야 하므로 테스트에 맞게 준비한 파일은 버전 관리 대상에 추가한다.

```java
@Test
void dataFileSumTest() {
 File dataFile = new File("src/test/resources/datafile.txt");
 long sum = MathUtils.sum(dataFile);
 asserEquals(101, sum);
}
```

- 파일을 미리 만들지 않고 테스트 코드에서 상황에 맞는 파일을 생성하는 방법

```java
@Test
void dataFileSumTest2() {
	givenDataFile("target/datafile.txt", "1", "2", "3", "4");
 File dataFile = new File("target/datafile.txt");
 long sum = MathUtils.sum(dataFile);
 asserEquals(101, sum);
}

private void givenDataFile(String path, String... lines) {
	try {
		Path dataPath = Paths.get(path);
		if(Files.exists(dataPath)) {
			Files.delete(dataPath);
		}
		Files.write(dataPath, Arrays.asList(lines));
	} catch(IOException e) {
		throw new RuntimeException(e);
	}
}
```

### 외부 상태가 테스트 결과에 영향을 주지 않게 하기

테스트 코드는 한 번만 실행하고 끝나지 않는다.

간헐적으로 실패하거나 성공하면 테스트 신뢰도가 떨어진다.

- 예): DB 데이터 상태에 따라 테스트가 성공하기도 하고 실패하는 경우

해결방법은 테스트 실행 전에 외부를 원하는 상태로 만들거나 테스트 실행 후에 외부 상태를 원래대로 되돌려 놓아야 한다.

### 외부 상태와 테스트 어려움

상황과 결과에 영향을 주는 외부 요인은 파일, DBMS, 외부 서버 REST API 등 다양하다.

외부 상황은 테스트 코드에서 마음대로 제어할 수 없는 경우도 있다.

테스트 대상의 상황과 결과에 외부 요인이 관여할 경우 **대역**을 사용하면 테스트 작성이 쉬워진다.

대역은 테스트 대상이 의존하는 대상의 실제 구현을 대신하는 구현인데 이 대역을 통해서 외부 상황이나 결과를 대체할 수 있다.