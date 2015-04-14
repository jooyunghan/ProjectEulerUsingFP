package com.jooyunghan;

import com.jooyunghan.functions.F1;
import com.jooyunghan.functions.S;

/**
 * Created by jooyung.han on 4/14/15.
 */
public class Option<A> {
    private final static Option<Object> NONE = new Option(null);
    private final A a;

    public Option(A a) {
        this.a = a;
    }

    public <B> Option<B> map(F1<A, B> f) {
        return isSome() ? some(f.apply(get())) : none();
    }

    public boolean isSome() {
        return a != null;
    }

    public A get() {
        return a;
    }

    public A getOrElse(S<A> a) {
        return isSome() ? get() : a.apply();
    }

    public static <A> Option<A> none() {
        return (Option<A>) NONE;
    }

    public static <A> Option<A> some(A a) {
        if (a == null)
            throw new NullPointerException();
        return new Option(a);
    }
}
