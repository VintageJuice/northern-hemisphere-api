package com.foy.northernhemisphereapi.config;

import com.foy.northernhemisphereapi.NorthernHemisphereApiConfiguration;
import com.foy.northernhemisphereapi.controller.NorthCountriesController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = NorthernHemisphereApiConfiguration.class)
@Import(NorthCountriesController.class)
class SecurityConfigurationTest {

    private final SecurityConfiguration securityConfiguration;

    @Autowired
    SecurityConfigurationTest(SecurityConfiguration securityConfiguration){
        this.securityConfiguration = securityConfiguration;
    }

    @Autowired
    private MockMvc mvc;

    @WithMockUser(value = "admin", roles = {"ADMIN"})
    @Test
    void getNorthCountriesWithAuth() throws Exception {
        mvc.perform(get("/northcountries").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void passwordEncoder() {
        String password = "expected";
        assertNotEquals(password, securityConfiguration.passwordEncoder().encode(password));
    }
}