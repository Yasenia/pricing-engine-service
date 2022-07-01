package io.github.yasenia.core.tool;

import org.junit.jupiter.api.RepeatedTest;

import static io.github.yasenia.core.tool.Times.currentTime;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class TimesTest {

    @RepeatedTest(5)
    void should_always_truncate_current_time_to_micro_second() {
        var time = currentTime();
        assertThat(time.getNano() % 1000, equalTo(0));
    }
}
