package com.jooyunghan;

import static com.jooyunghan.Pair.p;
import static com.jooyunghan.Stream.iterate;

public class Main {

    public static void main(String[] args) {
        euler2();
    }

    private static void euler2() {
        Stream<Integer> fibo = iterate(p(0, 1), pair -> p(pair._2, pair._1 + pair._2)).map(pair -> pair._1);
        Integer solution = fibo.filter(x -> x % 2 == 0).takeWhile(x -> x < 4000000).foldLeft(0, (a, b) -> a + b);
        System.out.println(solution);
    }
}
