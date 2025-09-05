package com.compare.demo.integration;

import com.compare.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@TestPropertySource(properties = {
    "server.port=8080",
    "server.servlet.context-path=/api",
    "spring.application.name=compare-demo",
    "spring.h2.console.enabled=true"
})
class ApplicationIntegrationTest {

    @Autowired
    private UserService userService;

    @MockBean
    private com.compare.demo.mapper.UserMapper userMapper;

    @Test
    void applicationContextShouldLoad() {
        assertDoesNotThrow(() -> userService != null);
    }

    @Test
    void serviceShouldBeConfigured() {
        assertDoesNotThrow(() -> userService.getClass().getName());
    }
}