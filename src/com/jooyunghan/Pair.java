package com.jooyunghan;

/**
 * Created by jooyung.han on 4/13/15.
 */
public class Pair<A, B> {
    public final A _1;
    public final B _2;

    public Pair(A a, B b) {
        _1 = a;
        _2 = b;
    }

    public static <A, B> Pair<A, B> p(A a, B b) {
        return new Pair<>(a, b);
    }

    @Override
    public String toString() {
        return "Pair{" +
                "_1=" + _1 +
                ", _2=" + _2 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        if (_1 != null ? !_1.equals(pair._1) : pair._1 != null) return false;
        return !(_2 != null ? !_2.equals(pair._2) : pair._2 != null);

    }

    @Override
    public int hashCode() {
        int result = _1 != null ? _1.hashCode() : 0;
        result = 31 * result + (_2 != null ? _2.hashCode() : 0);
        return result;
    }
}
