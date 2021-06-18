package com.foy.northernhemisphereapi.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NorthCountriesResponseDtoTest {

    @Test
    void getNorthCountries() {
        List<String> countries = List.of("Germany, England");
        NorthCountriesResponseDto northCountriesResponseDto = new NorthCountriesResponseDto();
        northCountriesResponseDto.setNorthCountries(countries);
        assertEquals(countries, northCountriesResponseDto.getNorthCountries());
    }
}