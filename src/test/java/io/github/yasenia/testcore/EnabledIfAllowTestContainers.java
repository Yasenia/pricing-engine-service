package io.github.yasenia.testcore;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DisabledIfSystemProperty(
    named = "test.containers.enabled", matches = "false",
    disabledReason = "Tests with containers are disabled. Set `test.containers.enabled=true` in the gradle.properties to enable these tests."
)
@Test
public @interface EnabledIfAllowTestContainers {
}
