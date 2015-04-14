package com.jooyunghan.stream;

import com.jooyunghan.Option;
import com.jooyunghan.Pair;
import com.jooyunghan.functions.A1;
import com.jooyunghan.functions.F1;
import com.jooyunghan.functions.F2;
import com.jooyunghan.functions.S;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.jooyunghan.Option.none;
import static com.jooyunghan.Option.some;
import static com.jooyunghan.Pair.p;
import static com.jooyunghan.functions.Functions.equalTo;
import static com.jooyunghan.functions.Functions.not;

/**
 * Created by jooyung.han on 4/13/15.
 */


public abstract class Stream<A> {
    final static Empty EMPTY = new Empty();

    public abstract boolean isEmpty();

    public abstract A head();

    public abstract Stream<A> tail();

    public static <A> Stream<A> stream(A... as) {
        return stream0(0, as);
    }

    public static <A> Stream<A> stream(Iterable<A> i) {
        return unfold(i.iterator(), it -> it.hasNext() ? some(p(it.next(), it)) : none());
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

    public static <A> Stream<A> iterate(A a, F1<A, A> f) {
        return cons(() -> a, () -> iterate(f.apply(a), f));
    }

    public static <A, B> Stream<A> unfold(B b, F1<B, Option<Pair<A, B>>> f) {
        Option<Pair<A, B>> result = f.apply(b);
        return result.map(pair -> cons(() -> pair._1, () -> unfold(pair._2, f))).getOrElse(() -> empty());
    }

    public void each(A1<A> f) {
        Stream<A> c = this;
        while (!c.isEmpty()) {
            f.apply(c.head());
            c = c.tail();
        }
    }

    public Stream<A> append(S<Stream<A>> ap) {
        return isEmpty() ? ap.apply() : cons(() -> head(), () -> tail().append(ap));
    }

    public <B> Stream<B> flatMap(F1<A, Stream<B>> f) {
        return (isEmpty()) ? empty() : f.apply(head()).append(() -> tail().flatMap(f));
    }

    public <B> Stream<B> map(F1<A, B> f) {
        return (isEmpty()) ? empty() : cons(() -> f.apply(head()), () -> tail().map(f));
    }

    public <B, C> Stream<C> zip(Stream<B> bs, F2<A, B, C> f) {
        return (isEmpty() || bs.isEmpty()) ? empty() : cons(() -> f.apply(head(), bs.head()), () -> tail().zip(bs.tail(), f));
    }

    public <B> Stream<B> pairs(F2<A, A, B> f) {
        return zip(tail(), f);
    }

    public Stream<A> filter(F1<A, Boolean> f) {
        Stream<A> c = dropWhile(not(f));
        return c.isEmpty() ? empty() : cons(() -> c.head(), () -> c.tail().filter(f));
    }

    public Stream<A> take(int n) {
        if (!isEmpty() && n > 0) {
            return cons(() -> head(), () -> tail().take(n - 1));
        } else {
            return empty();
        }
    }

    public Stream<A> takeWhile(F1<A, Boolean> f) {
        if (!isEmpty() && f.apply(head())) {
            return cons(() -> head(), () -> tail().takeWhile(f));
        } else {
            return empty();
        }
    }

    public Stream<A> dropWhile(F1<A, Boolean> f) {
        Stream<A> c = this;
        while (!c.isEmpty() && f.apply(c.head())) {
            c = c.tail();
        }
        return c;
    }

    public Pair<Stream<A>, Stream<A>> span(F1<A, Boolean> f) {
        return p(takeWhile(f), dropWhile(f));
    }

    public Stream<Stream<A>> group() {
        if (isEmpty()) {
            return empty();
        } else {
            Pair<Stream<A>, Stream<A>> partition = span(equalTo(head()));
            return cons(() -> partition._1, () -> partition._2.group());
        }
    }

    public <B> B foldLeft(B b, F2<B, A, B> f) {
        Stream<A> c = this;
        while (!c.isEmpty()) {
            b = f.apply(b, c.head());
            c = c.tail();
        }
        return b;
    }

    public A foldLeft(F2<A, A, A> f) {
        return tail().foldLeft(head(), f);
    }

    public <B> B foldRight(S<B> b, F2<A, S<B>, B> f) {
        return isEmpty() ? b.apply() : f.apply(head(), () -> tail().foldRight(b, f));
    }

    public A foldRight(F2<A, S<A>, A> f) {
        return tail().isEmpty() ? head() : f.apply(head(), () -> tail().foldRight(f));
    }

    public int length() {
        int length = 0;
        Stream<A> c = this;
        while (!c.isEmpty()) {
            length++;
            c = c.tail();
        }
        return length;
    }

    public A get(int n) {
        Stream<A> c = this;
        while (!c.isEmpty() && n > 0) {
            n--;
            c = c.tail();
        }
        if (c.isEmpty())
            throw new NoSuchElementException();
        return c.head();
    }

    public List<A> asList() {
        List<A> result = new ArrayList<>();
        each(result::add);
        return result;
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
