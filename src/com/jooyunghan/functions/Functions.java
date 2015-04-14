package com.jooyunghan.functions;

/**
 * Created by jooyung.han on 4/14/15.
 */
public class Functions {
    public static <A> F1<A, Boolean> equalTo(A a) {
        return x -> a.equals(x);
    }
}
