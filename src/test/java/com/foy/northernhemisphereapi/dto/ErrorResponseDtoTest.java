package com.foy.northernhemisphereapi.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorResponseDtoTest {

    @Test
    void builder() {
        String errorTest = "error";
        int errorCode = 404;
        LocalDateTime errorTime = LocalDateTime.now();
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .error(errorTest)
                .message(errorTest)
                .path(errorTest)
                .status(errorCode)
                .timestamp(errorTime)
                .build();

        assertEquals(errorTest, errorResponseDto.getError());
        assertEquals(errorTest, errorResponseDto.getMessage());
        assertEquals(errorTest, errorResponseDto.getPath());
        assertEquals(errorCode, errorResponseDto.getStatus());
        assertEquals(errorTime, errorResponseDto.getTimestamp());
    }

    @Test
    void setData() {
        String errorTest = "error";
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setError(errorTest);
        assertEquals(errorTest, errorResponseDto.getError());
    }
}