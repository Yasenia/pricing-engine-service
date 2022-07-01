import org.flywaydb.gradle.task.FlywayMigrateTask
import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    java
    `java-test-fixtures`
    checkstyle
    jacoco
    id("org.springframework.boot") version "3.0.0-M5"
    id("org.flywaydb.flyway") version "9.3.1"
    id("nu.studer.jooq") version "7.1.1"
    id("org.owasp.dependencycheck") version "7.1.1"
}

repositories {
    maven { url = uri("https://repo.spring.io/milestone") }
    mavenCentral()
}

dependencies {
    implementation(platform(SpringBootPlugin.BOM_COORDINATES))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("com.vladmihalcea:hibernate-types-60:2.19.1")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.flywaydb:flyway-core")

    testImplementation(platform("org.testcontainers:testcontainers-bom:1.17.3"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.testcontainers:testcontainers")
    testImplementation("org.testcontainers:postgresql")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.mockito:mockito-inline")

    jooqGenerator(platform(SpringBootPlugin.BOM_COORDINATES))
    jooqGenerator("org.postgresql:postgresql")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(19))
}

checkstyle {
    maxWarnings = 0
    toolVersion = "10.3"
}

jacoco {
    toolVersion = "0.8.8"
}

dependencyCheck {
    analyzers.assemblyEnabled = false
}

jooq {
    configurations.create("main").jooqConfiguration.apply {
        jdbc.apply {
            url = getProperty("ddl-db.url")
            user = getProperty("ddl-db.user")
            password = getProperty("ddl-db.password")
        }
        generator.database.apply {
            inputSchema = "public"
            excludes = "flyway_schema_history"
        }
    }
}

tasks {
    test {
        systemProperties(project.properties.filterKeys { it.startsWith("test.") })
        useJUnitPlatform()
    }

    withType(JacocoReportBase::class.java) {
        dependsOn(test)
        classDirectories.setFrom(files(classDirectories.files.map { fileTree(it) { exclude("org/jooq/generated/**", "**/*Bootstrap.*") } }))
    }

    jacocoTestCoverageVerification {
        violationRules.rule { limit { minimum = 0.9.toBigDecimal() } }
    }

    checkstyleMain {
        exclude("org/jooq/generated/**")
    }

    val flywayMigrateDdlDatabase = create<FlywayMigrateTask>("flywayMigrateDdlDatabase") {
        url = getProperty("ddl-db.url")
        user = getProperty("ddl-db.user")
        password = getProperty("ddl-db.password")
    }

    named("generateJooq") {
        dependsOn(flywayMigrateDdlDatabase)
    }
}

fun getProperty(key: String): String? = properties[key] as? String?
