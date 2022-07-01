package io.github.yasenia;

import io.github.yasenia.testcore.DatabaseTestConfiguration;
import io.github.yasenia.testcore.EnabledIfAllowTestContainers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(DatabaseTestConfiguration.class)
@EnabledIfAllowTestContainers
public class SmokeTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_response_ok_when_health_check_given_application_started_up() throws Exception {
        mockMvc
            .perform(get("/actuator/health"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("UP"))
        ;
    }
}
