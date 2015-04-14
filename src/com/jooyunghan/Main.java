package com.jooyunghan;

import static com.jooyunghan.Pair.p;
import static com.jooyunghan.Stream.iterate;
import static com.jooyunghan.Stream.stream;

public class Main {

    public static void main(String[] args) {
        ant();
    }

    private static void ant() {
        Stream<Stream<Integer>> ants = iterate(stream(1), s -> s.group().flatMap(g -> stream(g.length(), g.head())));
        System.out.println(ants.get(1000_000).take(100).asList());
    }

    private static void euler2() {
        Stream<Integer> fibo = iterate(p(0, 1), pair -> p(pair._2, pair._1 + pair._2)).map(pair -> pair._1);
        Integer solution = fibo.filter(x -> x % 2 == 0).takeWhile(x -> x < 4000000).foldLeft(0, (a, b) -> a + b);
        System.out.println(solution);
    }
}
