package com.jooyunghan;

import com.jooyunghan.stream.Stream;
import com.jooyunghan.stream.Strings;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.jooyunghan.Pair.p;
import static com.jooyunghan.stream.Stream.iterate;
import static com.jooyunghan.stream.Stream.stream;
import static com.jooyunghan.stream.Strings.lines;
import static com.jooyunghan.stream.Strings.words;

/**
 * Created by jooyung.han on 4/14/15.
 */
public class Eulers {
    public static boolean isPalindrome(int n) {
        return isPalindrome(Integer.toString(n));
    }

    public static boolean isPalindrome(String s) {
        return s.equals(Strings.reverse(s));
    }

    public static Stream<Integer> fibonacci() {
        return iterate(p(0, 1), pair -> p(pair._2, pair._1 + pair._2)).map(pair -> pair._1);
    }

    public static boolean isPythagorean(int a, int b, int c) {
        return a * a + b * b == c * c;
    }

    public static Stream<Stream<Integer>> readFile(String filename) {
        try {
            return stream(Files.readAllLines(Paths.get(filename), Charset.defaultCharset())).map(line -> words(line).map(Integer::parseInt));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Stream<Stream<Integer>> readString(String content) {
        return lines(content).map(line -> words(line).map(Integer::parseInt));
    }

}
