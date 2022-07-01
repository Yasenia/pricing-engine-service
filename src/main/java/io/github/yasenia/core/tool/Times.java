package io.github.yasenia.core.tool;

import java.time.OffsetDateTime;

import static java.time.OffsetDateTime.now;
import static java.time.temporal.ChronoUnit.MICROS;

public interface Times {

    /*
     * The timestamp stored in the database use microsecond precision, but the OffsetDateTime use nanosecond precision(depends on OS).
     * Use this method instead of OffsetDataTime.now() to generate current time to avoid time inconsistency between memory and database.
     * */
    static OffsetDateTime currentTime() {
        return now().truncatedTo(MICROS);
    }
}
