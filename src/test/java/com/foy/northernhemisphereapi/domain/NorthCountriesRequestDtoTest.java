package com.foy.northernhemisphereapi.domain;

import com.foy.northernhemisphereapi.dto.NorthCountriesRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class NorthCountriesRequestDtoTest {

    private final String TEST_MESSAGE = "8.8.8.8";
    private NorthCountriesRequestDto northCountriesRequestDto;

    @BeforeEach
    void setup() {
        this.northCountriesRequestDto = new NorthCountriesRequestDto(TEST_MESSAGE);
    }

    @Test
    void getMessage() {
        assertEquals(TEST_MESSAGE, northCountriesRequestDto.getIp());
    }

    @Test
    void setMessage() {
        String expected = "New Message";
        northCountriesRequestDto.setIp(expected);
        assertEquals(expected, northCountriesRequestDto.getIp());
    }

    @Test
    void testEquals() {
        NorthCountriesRequestDto otherNorthCountriesRequestDto = NorthCountriesRequestDto.builder().ip(TEST_MESSAGE).build();
        assertEquals(northCountriesRequestDto, otherNorthCountriesRequestDto);
    }

    @Test
    void testUnequal() {
        NorthCountriesRequestDto otherNorthCountriesRequestDto = NorthCountriesRequestDto.builder().ip("7.7.7.7").build();
        assertNotEquals(northCountriesRequestDto, otherNorthCountriesRequestDto);
    }
}