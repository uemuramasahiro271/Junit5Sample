package app.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

// パラメータ化テストはjunit-jupiter-params への依存を追加する必要がある。

public class ParameterizedTestSample {

	enum Fruits {
		  Apple,
		  Banana,
	}

	// @ValueSource

    @ParameterizedTest  // パラメータ化テスト。
    @ValueSource(strings = { "aa", "bb" })  // 引数に渡す候補
    void palindromes(String candidate) {
    	assertTrue(candidate.length() == 2);
    }

	@ParameterizedTest
	@MethodSource("range")
	void methodSourceIntStream(int value) {
		assertTrue(0 < value && value < 10);
	}

	static IntStream range() {
		return IntStream.range(1, 10);
	}

  	@ParameterizedTest
	@MethodSource({"fullNameProvider1", "fullNameProvider2"})
	void methodSourceStream(String fullName) {
		assertNotNull(fullName);
		assertTrue(fullName.contains(" "));
	}

	static Stream<String> fullNameProvider1() {
		return Stream.of("Ryosuke Uchitate", "Taro Uchitate");
	}
	static Stream<String> fullNameProvider2() {
		return Stream.of("Michael Jackson", "Janet Jackson");
	}

 	@ParameterizedTest
	@MethodSource("multiArgumentsProvider")
	void multiArguments(String name, int age, Fruits fruits) {
		assertFalse(name.length() > 100);
		assertTrue( age > 0 && age < 30);
		assertTrue(fruits.name().length() > 1);
	}

	static Stream<Arguments> multiArgumentsProvider() {
		return Stream.of(
				Arguments.of("Ryosuke", 27, Fruits.Apple),
				Arguments.of("Hanako", 20, Fruits.Banana)
		);
	}

    @Nested
    public class EnumSourceParameterizedTestSample {

        // @EnumSource
        @ParameterizedTest
        @EnumSource(Fruits.class)
        void enumSourceAll(Fruits fruits) {
    		assertTrue(Arrays.asList(Fruits.values()).contains(fruits));
    	}

        @ParameterizedTest
        @EnumSource(value = Fruits.class, names = {"Apple"})
        void enumSourceInclude(Fruits fruits) {
    		assertTrue(Arrays.asList(Fruits.values()).contains(fruits));
    	}

        @ParameterizedTest
        @EnumSource(value = Fruits.class, names = {"Apple"}, mode = Mode.EXCLUDE)
        void enumSourceExclude(Fruits fruits) {
        	assertFalse(Collections.singleton(Fruits.Apple).contains(fruits));
        	assertEquals(Fruits.Banana, fruits);
    	}

        @ParameterizedTest
        @EnumSource(value = Fruits.class, names = ".*Apple$", mode = Mode.MATCH_ALL)
        void enumSourceMatchAll(Fruits fruits) {
    		String name = fruits.name();
    		assertTrue(name.startsWith("A") || name.startsWith("B"));
    		assertTrue(name.contains("Apple"));
        }
    }

    @Nested
    public class CsvSourceParameterizedTestSample {

    	@ParameterizedTest
    	@CsvSource({ "foo, 1, aa", "bar, 2", "'baz, qux', 3" })
    	void testWithCsvSource(String first, int second) {
    	    assertNotNull(first);
    	    assertNotEquals(0, second);
    	}

    	@ParameterizedTest
    	@CsvSource({ "foo, 1", "bar, 2", "'baz, qux', 3" })
    	void testWithCsvSourceaa(ArgumentsAccessor arguments) {

    		String first = arguments.getString(0);
    		int second = arguments.getInteger(1);

    	    assertNotNull(first);
    	    assertNotEquals(0, second);
    	}

    	@ParameterizedTest
    	@CsvFileSource(resources = "/two-column.csv")
    	void testWithCsvFileSource(String first, int second) {
    	    assertNotNull(first);
    	    assertNotEquals(0, second);
    	}
    }
}
