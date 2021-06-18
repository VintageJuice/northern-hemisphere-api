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

@WebMvcTest(controllers = {NorthCountriesController.class})
class NorthCountriesControllerTest {

    private static final String NORTH_COUNTRIES_URL = "/northcountries";

    private final List<Country> countryList = List.of(new Country("United States"), new Country("United Kingdom"));
    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;

    @MockBean
    private NorthCountriesImpl northCountriesServiceMock;

    @Autowired
    NorthCountriesControllerTest(MockMvc mockMvc, ObjectMapper objectMapper){
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @WithMockUser(value = "admin", roles = {"ADMIN"})
    @Test
    void getNorthCountriesWithAuth() throws Exception {
        when(northCountriesServiceMock.getNorthCountries(any())).thenReturn(countryList);
        NorthCountriesResponseDto northCountriesResponseDto = convertToNorthCountriesResponseDto(countryList);
        String response = generateNorthCountriesResponse(northCountriesResponseDto);
        executeGetWithParamsSuccessfully(response);
    }

    @WithMockUser(value = "spring")
    @Test
    void getNorthCountriesWithoutAuth() throws Exception {
        when(northCountriesServiceMock.getNorthCountries(any())).thenReturn(countryList);
        NorthCountriesResponseDto northCountriesResponseDto = convertToNorthCountriesResponseDto(countryList);
        String response = generateNorthCountriesResponse(northCountriesResponseDto);
        executeGetWithParamsSuccessfully(response);
    }

    @WithMockUser(value = "admin", roles = {"ADMIN"})
    @Test
    void getNorthCountriesWithMaxValidationExceeded() throws Exception {
        mockMvc.perform(get(NORTH_COUNTRIES_URL)
                .queryParam("ip", "8.8.8.8")
                .queryParam("ip", "8.8.8.7")
                .queryParam("ip", "8.8.8.9")
                .queryParam("ip", "8.8.8.4")
                .queryParam("ip", "8.8.8.3")
                .queryParam("ip", "8.8.8.1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(mvcResult -> assertTrue(mvcResult.getResolvedException() instanceof ConstraintViolationException));
    }

    @WithMockUser(value = "admin", roles = {"ADMIN"})
    @Test
    void getNorthCountriesWithMinValidationNotMet() throws Exception {
        mockMvc.perform(get(NORTH_COUNTRIES_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(mvcResult -> assertTrue(mvcResult.getResolvedException() instanceof MissingServletRequestParameterException));
    }

    @WithMockUser(value = "admin", roles = {"ADMIN"})
    @Test
    void getNorthCountriesWithIncorrectIpFormatValidation() throws Exception {
        mockMvc.perform(get(NORTH_COUNTRIES_URL)
                .queryParam("ip", ".8.t.6.4")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(mvcResult -> assertTrue(mvcResult.getResolvedException() instanceof ConstraintViolationException));
    }

    private String generateNorthCountriesResponse(NorthCountriesResponseDto northCountriesResponseDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(northCountriesResponseDto);
    }

    private void executeGetWithParamsSuccessfully(String response) throws Exception {
        mockMvc.perform(get(NORTH_COUNTRIES_URL)
                .queryParam("ip", "8.8.8.8")
                .queryParam("ip", "8.8.8.7")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }
}