package io.github.yasenia.core.tool;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static io.github.yasenia.core.tool.Exceptions.rethrowFunction;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExceptionsTest {

    @Test
    void should_throw_the_checked_exception_given_some_exception_thrown() {
        assertThrows(CheckedException.class, () -> {
            //noinspection ResultOfMethodCallIgnored
            Stream.of(true, false, true).map(rethrowFunction(this::returnIfTrueOrElseThrow)).toList();
        });
    }

    @Test
    void should_not_throw_the_checked_exception_given_no_exception_thrown() {
        assertDoesNotThrow(() -> {
            //noinspection ResultOfMethodCallIgnored
            Stream.of(true, true, true).map(rethrowFunction(this::returnIfTrueOrElseThrow)).toList();
        });
    }

    private boolean returnIfTrueOrElseThrow(boolean test) throws CheckedException {
        if (test) return true;
        throw new CheckedException();
    }

    private static class CheckedException extends Exception {
    }
}
