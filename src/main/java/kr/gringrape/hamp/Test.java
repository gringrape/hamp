package kr.gringrape.hamp;

import org.apache.tomcat.util.json.JavaCharStream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Test {

    public static void main(String[] args) {

        // TODO stream api를 이용한 1~100 홀수 출력 -> clear

        IntStream
                .range(1, 100)
                .filter(i -> i % 2 == 1)
                .forEach(System.out::println);

        // TODO stream api를 이용하여 지정된 문자열의 홀수 번째 글자를 출력 -> clear

        String text = "I know what you did last summer you know that?";

        IntStream
                .range(0, text.length())
                .filter(i -> i % 2 == 0)
                .forEach(i -> System.out.print(text.charAt(i)));

        System.out.println();

        // TODO stream api 를 이용하여 세개의 문자열을 stream으로 변환후에 특정한 문자를 붙여서 출력

        Stream.of("a", "b", "c")
                .forEach(character -> System.out.println(character + " signature"));

        // TODO 일련의 문자열을 List로 변형

        List<String> testArray = Arrays.asList("a", "b", "c");

        System.out.print(testArray);

    }

}
