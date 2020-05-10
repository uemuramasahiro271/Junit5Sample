package app.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Sample {

	// @BeforeAllはstaticなメソッドである必要がある
	// テストクラスの開始時に1度実行される
	@BeforeAll
	static void setUpAll() {
		System.out.println("before all tests");
	}

	// @BeforeEachは各テスト開始時に実行される
	@BeforeEach
	void setup() {
		System.out.println("before each test");
	}

	@Test
	@DisplayName("test No.1")  // テストの名前を指定できる
	void test1() {
	    System.out.println("test 1");
	}

	@Test
	void test2() {
	    System.out.println("test 2");
	    fail("テスト失敗メッセージ");
	}

	@Test
	@Disabled  // テストがスキップされる
	void test3() {
	    System.out.println("test 3");
	}

	@Test
	void standardAssertions() {
		assertEquals(2, 2);
		assertEquals(4, 3, "テスト失敗メッセージ");
		assertTrue(true);
	}

	@Test
    void groupedAssertions() {
        // アサーションをグループ化すると、すべてのアサーションが一度に実行され、
        // すべての失敗がまとめて報告される。
        assertAll("person",
            () -> assertEquals("John", "John"),
            () -> assertEquals("Doe", "Do"),
            () -> assertEquals("Yuki", "Yuki")
        );
    }

    @Test
    void timeoutNotExceeded() {
        // 次のアサーションは成功する。
        assertTimeout(Duration.ofMinutes(2), () -> {
            // 2分未満で終わるタスクを実行する。
        });
    }

	@AfterEach
	void tearDown() {
	    System.out.println("after each test");
	}

	@AfterAll
	static void tearDownAll() {
	    System.out.println("after all tests");
	}
}
