package com.jooyunghan.fp;

import com.jooyunghan.fp.functions.F1;
import com.jooyunghan.fp.functions.S;

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

    @Override
    public String toString() {
        return "com.jooyunghan.fp.Option{" + a + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Option<?> option = (Option<?>) o;

        return !(a != null ? !a.equals(option.a) : option.a != null);

    }

    @Override
    public int hashCode() {
        return a != null ? a.hashCode() : 0;
    }
}
