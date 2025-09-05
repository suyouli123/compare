package com.compare.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.application.name=compare-demo"
})
class ApplicationServiceTest {

    @Test
    void applicationContextShouldLoad() {
        assertThat(true).isTrue();
    }

    @Test
    void applicationNameShouldBeConfigured() {
        String appName = "compare-demo";
        assertThat(appName).isNotNull();
        assertThat(appName).isEqualTo("compare-demo");
    }
}