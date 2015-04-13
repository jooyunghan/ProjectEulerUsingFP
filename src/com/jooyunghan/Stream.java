package com.jooyunghan;

import com.jooyunghan.functions.A1;
import com.jooyunghan.functions.F1;
import com.jooyunghan.functions.F2;
import com.jooyunghan.functions.S;

import java.util.NoSuchElementException;

/**
 * Created by jooyung.han on 4/13/15.
 */


public abstract class Stream<A> {
    final static Empty EMPTY = new Empty();


    public static <A> Stream<A> stream(A... as) {
        return stream0(0, as);
    }

    private static <A> Stream<A> stream0(int offset, A... as) {
        if (offset >= as.length) {
            return empty();
        } else {
            return cons(() -> as[offset], () -> stream0(offset + 1, as));
        }

    }

    public static <A> Stream<A> cons(S<A> head, S<Stream<A>> tail) {
        return new Cons(head, tail);
    }

    public static <A> Stream<A> empty() {
        return (Stream<A>) EMPTY;
    }

    public abstract boolean isEmpty();

    public abstract A head();

    public abstract Stream<A> tail();

    public void each(A1<A> f) {
        Stream<A> c = this;
        while (!c.isEmpty()) {
            f.apply(c.head());
            c = c.tail();
        }
    }

    public <B> Stream<B> map(F1<A, B> f) {
        return (isEmpty()) ? empty() : cons(() -> f.apply(head()), () -> tail().map(f));
    }

    public Stream<A> filter(F1<A, Boolean> f) {
        Stream<A> c = this;
        while (!c.isEmpty()) {
            if (f.apply(c.head())) {
                A head = c.head();
                Stream<A> tail = c.tail();
                return cons(() -> head, () -> tail.filter(f));
            }
            c = c.tail();
        }
        return empty();
    }

    public Stream<A> takeWhile(F1<A, Boolean> f) {
        if (!isEmpty() && f.apply(head())) {
            return cons(() -> head(), ()->tail().takeWhile(f));
        } else {
            return empty();
        }
    }

    public static <A> Stream<A> iterate(A a, F1<A, A> f) {
        return cons(() -> a, () -> iterate(f.apply(a), f));
    }

    public <B> B foldLeft(B b, F2<B, A, B> f) {
        Stream<A> c = this;
        while (!c.isEmpty()) {
            b = f.apply(b, c.head());
            c = c.tail();
        }
        return b;
    }

    static class Cons<A> extends Stream<A> {
        private S<A> head;
        private S<Stream<A>> tail;
        private A head_;
        private Stream<A> tail_;

        public Cons(S<A> head, S<Stream<A>> tail) {

            this.head = head;
            this.tail = tail;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public A head() {
            if (head_ == null) {
                head_ = head.apply();
                head = null;
            }
            return head_;
        }

        @Override
        public Stream<A> tail() {
            if (tail_ == null) {
                tail_ = tail.apply();
                tail = null;
            }
            return tail_;
        }
    }

    static class Empty extends Stream<Object> {

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public Object head() {
            throw new NoSuchElementException();
        }

        @Override
        public Stream<Object> tail() {
            throw new NoSuchElementException();
        }
    }

}
