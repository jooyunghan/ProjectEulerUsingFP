package com.jooyunghan.stream;

import static com.jooyunghan.stream.Stream.iterate;

/**
 * Created by jooyung.han on 4/14/15.
 */
public class Integers {
    public static Stream<Integer> range(int begin, int end) {
        return from(begin).takeWhile(n -> n <= end);
    }

    public static Stream<Integer> from(int begin) {
        return iterate(begin, x -> x + 1);
    }

}
