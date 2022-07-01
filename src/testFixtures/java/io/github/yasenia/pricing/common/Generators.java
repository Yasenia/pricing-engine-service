package io.github.yasenia.pricing.common;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class Generators {

    public static <T> List<T> aNotEmptyListOf(Supplier<T> supplier) {
        return aListOf(Randoms.RANDOM.nextInt(9) + 1, supplier);
    }

    public static <T> List<T> aListOf(Supplier<T> supplier) {
        return aListOf(Randoms.RANDOM.nextInt(10), supplier);
    }

    public static <T> List<T> aListOf(int size, Supplier<T> supplier) {
        return IntStream.range(0, size).mapToObj(i -> supplier.get()).toList();
    }
}
