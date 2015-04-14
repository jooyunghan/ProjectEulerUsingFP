package com.jooyunghan.fp;

import com.jooyunghan.fp.functions.F1;
import com.jooyunghan.fp.stream.Stream;
import org.junit.Test;

import static com.jooyunghan.fp.Option.none;
import static com.jooyunghan.fp.Option.some;
import static com.jooyunghan.fp.stream.Stream.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

interface Monad<M> {
    <T> M unit(T t);

    <S, T> M flatMap(M t, F1<T, M> f);

    default <S, T> M map(M t, F1<T, S> f) {
        return flatMap(t, (T x) -> unit(f.apply(x)));
    }
}

/**
 * Created by jooyung.han on 4/14/15.
 */
public class OptionMonadTest {
    @Test
    public void sequence() throws Exception {
        Monad<Option> optionMonad = new Monad<Option>() {
            @Override
            public <T> Option<T> unit(T t) {
                return some(t);
            }

            @Override
            public <S, T> Option<S> flatMap(Option t, F1<T, Option> f) {
                return t.flatMap(f);
            }
        };
        assertThat(sequence(stream(some(1), some(2)), optionMonad), is(some(stream(1, 2))));
        assertThat(sequence(stream(some(1), none()), optionMonad), is(none()));
    }

    private static <O> O sequence(Stream<O> str, Monad<O> m) {
        return str.isEmpty() ? m.unit(empty()) : m.flatMap(str.head(), (O s) -> m.map(sequence(str.tail(), m), (Stream<O> ss) -> cons(() -> s, () -> ss)));
    }
}