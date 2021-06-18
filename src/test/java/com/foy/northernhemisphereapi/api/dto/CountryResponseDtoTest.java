package com.foy.northernhemisphereapi.api.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryResponseDtoTest {

    @Test
    void builder() {
        String testStub = "value";
        CountryResponseDto countryResponseDto = CountryResponseDto.builder()
                .status(testStub)
                .country(testStub)
                .countryCode(testStub)
                .region(testStub)
                .regionName(testStub)
                .city(testStub)
                .zip(testStub)
                .lat(testStub)
                .lon(testStub)
                .timezone(testStub)
                .isp(testStub)
                .org(testStub)
                .as(testStub)
                .message(testStub)
                .query(testStub)
                .build();

        assertEquals(testStub, countryResponseDto.getStatus());
        assertEquals(testStub, countryResponseDto.getCountry());
        assertEquals(testStub, countryResponseDto.getCountryCode());
        assertEquals(testStub, countryResponseDto.getRegion());
        assertEquals(testStub, countryResponseDto.getRegionName());
        assertEquals(testStub, countryResponseDto.getCity());
        assertEquals(testStub, countryResponseDto.getZip());
        assertEquals(testStub, countryResponseDto.getLat());
        assertEquals(testStub, countryResponseDto.getLon());
        assertEquals(testStub, countryResponseDto.getTimezone());
        assertEquals(testStub, countryResponseDto.getIsp());
        assertEquals(testStub, countryResponseDto.getOrg());
        assertEquals(testStub, countryResponseDto.getAs());
        assertEquals(testStub, countryResponseDto.getQuery());
    }
}