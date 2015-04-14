package com.jooyunghan;

import com.jooyunghan.functions.F2;

import static com.jooyunghan.Stream.iterate;
import static com.jooyunghan.Stream.stream;

/**
 * Created by jooyung.han on 4/14/15.
 */
public class Streams {
    public static Stream<Integer> range(int begin, int end) {
        return from(begin).takeWhile(n -> n <= end);
    }

    public static Stream<Integer> from(int begin) {
        return iterate(begin, x -> x + 1);
    }

    public static <A extends Comparable<A>> F2<A, A, A> max() {
        return (a, b) -> (a.compareTo(b) > 0) ? a : b;
    }

    public static F2<Integer, Integer, Integer> add() {
        return (a, b) -> a + b;
    }

    public static Stream<String> words(String s) {
        return stream(s.split("\\s"));
    }

    public static Stream<String> lines(String s) {
        return stream(s.split("\\n"));
    }

}
