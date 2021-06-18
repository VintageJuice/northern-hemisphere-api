package com.foy.northernhemisphereapi.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SecurityConfigurationTest {

    private final SecurityConfiguration securityConfiguration;

    @Autowired
    public SecurityConfigurationTest(SecurityConfiguration securityConfiguration) {
        this.securityConfiguration = securityConfiguration;
    }

    @Test
    void passwordEncoder() {
        String password = "expected";
        assertNotEquals(password, securityConfiguration.passwordEncoder().encode(password));
    }
}