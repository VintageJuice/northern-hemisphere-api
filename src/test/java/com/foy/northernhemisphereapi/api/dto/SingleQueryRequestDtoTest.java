package com.foy.northernhemisphereapi.api.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingleQueryRequestDtoTest {

    @Test
    void builder() {
        String testSub = "value";
        SingleQueryRequestDto singleQueryRequestDto = SingleQueryRequestDto.builder()
                .query(testSub)
                .fields(testSub)
                .lang(testSub)
                .build();
        assertEquals(testSub, singleQueryRequestDto.getQuery());
        assertEquals(testSub, singleQueryRequestDto.getFields());
        assertEquals(testSub, singleQueryRequestDto.getLang());
    }

    @Test
    void testData() {
        SingleQueryRequestDto singleQueryRequestDto = new SingleQueryRequestDto();
        singleQueryRequestDto.setQuery("8.8.8.8");
        assertEquals("8.8.8.8", singleQueryRequestDto.getQuery());
    }
}