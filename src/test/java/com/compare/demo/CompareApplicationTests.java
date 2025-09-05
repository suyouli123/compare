package com.compare.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = {
    "server.port=8080",
    "server.servlet.context-path=/api",
    "spring.application.name=compare-demo"
})
class CompareApplicationTests {

    @Test
    void contextLoads() {
        assertThat(true).isTrue();
    }

    @Test
    void applicationPropertiesShouldBeLoaded() {
        assertThat(System.getProperty("spring.application.name")).isNotNull();
    }
}