package com.jooyunghan.fp;

import com.jooyunghan.fp.functions.S;
import com.jooyunghan.fp.stream.Stream;
import org.junit.Test;

import static com.jooyunghan.fp.stream.Stream.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by jooyung.han on 4/17/15.
 */
public class StreamTest {
    @Test
    public void streamEvaluatesLazily() throws Exception {
        final int[] count = {0};
        Stream<Integer> stream = stream(1, 2, 3);

        Stream<Integer> mapped = stream.map(i ->
                {
                    count[0]++;
                    return i + 1;
                }
        );

        assertThat(count[0], is(0));

        int result = mapped.take(1).get(0);
        assertThat(result, is(2));
        assertThat(count[0], is(1));
    }

    @Test
    public void doNotEvaluateForLength() throws Exception {
        S<Object> error = () -> {
            throw new RuntimeException();
        };
        Stream<?> list = cons(error, () -> cons(error, ()->empty()));
        assertThat(list.length(), is(2));
    }

    @Test
    public void takeFive() throws Exception {
        Stream<Integer> s = countDown(4);// 4, 3, 2, 1, 0...
        s.map(n -> sqrt(n)).take(5).asList();
    }

    private double sqrt(double n) {
        if (n < 0.0)
            throw new IllegalArgumentException("Squareroot of negative number");
        return Math.sqrt(n);
    }

    private Stream<Integer> countDown(int start) {
        return iterate(start, n -> n - 1);
    }
}
