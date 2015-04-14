package com.jooyunghan;

import com.jooyunghan.fp.functions.Functions;
import com.jooyunghan.fp.stream.Integers;
import com.jooyunghan.fp.stream.Stream;

import static com.jooyunghan.Eulers.*;
import static com.jooyunghan.fp.stream.Stream.iterate;
import static com.jooyunghan.fp.stream.Stream.stream;

public class Main {

    public static void main(String[] args) {
        System.out.println(ant());
        System.out.println(euler2());
        System.out.println(euler4());
        System.out.println(euler9());
        System.out.println(euler18_67());
    }

    private static Integer ant() {
        Stream<Stream<Integer>> ants = iterate(stream(1), s -> s.group().flatMap(g -> stream(g.length(), g.head())));
        return ants.get(1_000_000).get(1_000_000);
    }

    private static int euler2() {
        Stream<Integer> fibo = fibonacci();
        return fibo.filter(x -> x % 2 == 0).takeWhile(x -> x < 4_000_000).foldLeft(Functions.add());
    }

    private static int euler4() {
        return Integers.range(100, 999).flatMap(n -> Integers.range(n, 999).map(m -> n * m).filter(x -> isPalindrome(x))).foldLeft(Functions.max());
    }

    private static int euler9() {
        return Integers.range(1, 332).flatMap(a -> Integers.range(a + 1, (1000 - a) / 2).filter(b -> isPythagorean(a, b, 1000 - a - b)).map(b -> a * b * (1000 - a - b))).head();
    }

    private static int euler18_67() {
//        Stream<Stream<Integer>> data = stream(
//                stream(3),
//                stream(7, 4),
//                stream(2, 4, 6),
//                stream(8, 5, 9, 3)
//        );

//        Stream<Stream<Integer>> data = readString(
//                "75\n" +
//                "95 64\n" +
//                "17 47 82\n" +
//                "18 35 87 10\n" +
//                "20 04 82 47 65\n" +
//                "19 01 23 75 03 34\n" +
//                "88 02 77 73 07 63 67\n" +
//                "99 65 04 28 06 16 70 92\n" +
//                "41 41 26 56 83 40 80 70 33\n" +
//                "41 48 72 33 47 32 37 16 94 29\n" +
//                "53 71 44 65 25 43 91 52 97 51 14\n" +
//                "70 11 33 28 77 73 17 78 39 68 17 57\n" +
//                "91 71 52 38 17 14 91 43 58 50 27 29 48\n" +
//                "63 66 04 68 89 53 67 30 73 16 69 87 40 31\n" +
//                "04 62 98 27 23 09 70 98 73 93 38 53 60 04 23");

        Stream<Stream<Integer>> data = readFile("p067_triangle.txt");
        return data.foldRight((prev, next) -> prev.zip(next.apply().pairs(Functions.max()), Functions.add())).head();
    }
}
