package io.github.yasenia.core.tool;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DevelopmentsTest {

    @Test
    void should_throw_exception_when_call_the_todo_method() {
        assertThrows(UnsupportedOperationException.class, Developments::todo);
    }
}
