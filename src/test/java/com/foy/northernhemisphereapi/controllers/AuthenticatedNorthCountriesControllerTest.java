package com.foy.northernhemisphereapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foy.northernhemisphereapi.domain.Country;
import com.foy.northernhemisphereapi.dto.NorthCountriesResponseDto;
import com.foy.northernhemisphereapi.services.NorthCountriesImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MissingServletRequestParameterException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static com.foy.northernhemisphereapi.utils.NorthCountriesHemisphereApiMapper.convertToNorthCountriesResponseDto;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {AuthenticatedNorthCountriesController.class})
class AuthenticatedNorthCountriesControllerTest {

    private static final String AUTH_NORTH_COUNTRIES_URL = "/auth/northcountries";

    private final List<Country> countryList = List.of(new Country("United States"), new Country("United Kingdom"));
    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;

    @MockBean
    private NorthCountriesImpl northCountriesServiceMock;

    @Autowired
    AuthenticatedNorthCountriesControllerTest(ObjectMapper objectMapper, MockMvc mockMvc) {
        this.objectMapper = objectMapper;
        this.mockMvc = mockMvc;
    }

    @WithMockUser(value = "admin", roles = {"ADMIN"})
    @Test
    void getAuthenticatedNorthCountriesLoginWithAuth() throws Exception {
        when(northCountriesServiceMock.getAuthenticatedNorthCountries(any())).thenReturn(countryList);
        NorthCountriesResponseDto northCountriesResponseDto = convertToNorthCountriesResponseDto(countryList);
        String response = generateAuthenticatedNorthCountriesResponse(northCountriesResponseDto);
        executeGetWithParamsSuccessfully(response);
    }

    @WithMockUser(value = "spring")
    @Test
    void getAuthenticatedNorthCountriesLoginWithoutAuth() throws Exception {
        executeGetWithExpectedResponseIsForbidden();
    }

    @WithMockUser(value = "admin", roles = {"ADMIN"})
    @Test
    void getAuthenticatedNorthCountriesWithMaxValidationExceeded() throws Exception {
        executeGetWithMaxParamsValidationError();
    }

    @WithMockUser(value = "admin", roles = {"ADMIN"})
    @Test
    void getAuthenticatedNorthCountriesWithMinValidationNotMet() throws Exception {
        executeGetWithMinParamsValidationError();
    }

    @WithMockUser(value = "admin", roles = {"ADMIN"})
    @Test
    void getAuthenticatedNorthCountriesWithIncorrectIpFormatValidation() throws Exception {
        executeGetWithIncorrectIpFormat();
    }

    private String generateAuthenticatedNorthCountriesResponse(NorthCountriesResponseDto northCountriesResponseDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(northCountriesResponseDto);
    }

    private void executeGetWithParamsSuccessfully(String response) throws Exception {
        mockMvc.perform(get(AUTH_NORTH_COUNTRIES_URL)
                .queryParam("ip", "201.8.8.8")
                .queryParam("ip", "201.8.8.7")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    private void executeGetWithMaxParamsValidationError() throws Exception {
        mockMvc.perform(get(AUTH_NORTH_COUNTRIES_URL)
                .queryParam("ip", "201.8.8.8")
                .queryParam("ip", "201.8.8.7")
                .queryParam("ip", "201.8.8.9")
                .queryParam("ip", "201.8.8.4")
                .queryParam("ip", "201.8.8.3")
                .queryParam("ip", "201.8.8.1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(mvcResult -> assertTrue(mvcResult.getResolvedException() instanceof ConstraintViolationException));
    }

    private void executeGetWithMinParamsValidationError() throws Exception {
        mockMvc.perform(get(AUTH_NORTH_COUNTRIES_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(mvcResult -> assertTrue(mvcResult.getResolvedException() instanceof MissingServletRequestParameterException));
    }

    private void executeGetWithIncorrectIpFormat() throws Exception {
        mockMvc.perform(get(AUTH_NORTH_COUNTRIES_URL)
                .queryParam("ip", ".8.t.6.4")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(mvcResult -> assertTrue(mvcResult.getResolvedException() instanceof ConstraintViolationException));
    }

    private void executeGetWithExpectedResponseIsForbidden() throws Exception {
        mockMvc.perform(get(AUTH_NORTH_COUNTRIES_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}