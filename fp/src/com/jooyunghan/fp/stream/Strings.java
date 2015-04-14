package com.jooyunghan.fp.stream;

import static com.jooyunghan.fp.stream.Stream.stream;

/**
 * Created by jooyung.han on 4/14/15.
 */
public class Strings {

    public static Stream<String> words(String s) {
        return stream(s.split("\\s"));
    }

    public static Stream<String> lines(String s) {
        return stream(s.split("\\n"));
    }

    public static String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }
}
