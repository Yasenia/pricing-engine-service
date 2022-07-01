package io.github.yasenia.core.tool;

import java.util.function.Function;

/**
 * Make the checked exceptions can rethrow in the stream lambdas.
 * */
public interface Exceptions {

    @SuppressWarnings("RedundantThrows")
    static <T, R, E extends Exception> Function<T, R> rethrowFunction(ThrowableFunction<T, R, E> throwableFunction) throws E {
        return t -> {
            try {
                return throwableFunction.apply(t);
            } catch (Exception e) {
                throwActualException(e);
                return null;
            }
        };
    }

    @SuppressWarnings("unchecked")
    private static <E extends Exception> void throwActualException(Exception throwable) throws E {
        throw (E) throwable;
    }

    @FunctionalInterface
    interface ThrowableFunction<T, R, E extends Exception> {
        R apply(T t) throws E;
    }
}
