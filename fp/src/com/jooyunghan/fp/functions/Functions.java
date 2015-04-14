package com.jooyunghan.fp.functions;

/**
 * Created by jooyung.han on 4/14/15.
 */
public class Functions {
    public static <A> F1<A, Boolean> equalTo(A a) {
        return x -> a.equals(x);
    }

    public static <A> F1<A, Boolean> not(F1<A, Boolean> f) {
        return a -> !f.apply(a);
    }

    public static <A extends Comparable<A>> F2<A, A, A> max() {
        return (a, b) -> (a.compareTo(b) > 0) ? a : b;
    }

    public static F2<Integer, Integer, Integer> add() {
        return (a, b) -> a + b;
    }
}
