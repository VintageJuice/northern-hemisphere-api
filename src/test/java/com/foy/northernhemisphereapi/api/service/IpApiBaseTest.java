package com.foy.northernhemisphereapi.api.service;

import com.foy.northernhemisphereapi.api.config.IpApiServicesConfig;
import com.foy.northernhemisphereapi.api.dto.CountryResponseDto;
import com.foy.northernhemisphereapi.api.dto.SingleQueryRequestDto;
import com.foy.northernhemisphereapi.utils.ConfigConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest()
@ContextConfiguration(classes = IpApiServicesConfig.class)
class IpApiBaseTest {

    private static final String mockApi = "https://mock-api.com";
    private final IpApiBase ipApiBase;

    @MockBean
    private RestTemplate restTemplateMock;

    @Autowired
    public IpApiBaseTest(IpApiBase ipApiBase) {
        this.ipApiBase = ipApiBase;
    }

    @Test
    void postForObject() {
        CountryResponseDto expectedCountryResponse = CountryResponseDto.builder()
                .status("success")
                .country("United States")
                .lat("39.03").build();

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<SingleQueryRequestDto> queryList = List.of(new SingleQueryRequestDto("8.8.8.8", "", ""));
        HttpEntity<List<SingleQueryRequestDto>> request = new HttpEntity<>(queryList, headers);

        when(restTemplateMock.postForObject(mockApi, request, CountryResponseDto.class)).thenReturn(expectedCountryResponse);
        CountryResponseDto countryResponseDto = ipApiBase.postForObject(mockApi, request, CountryResponseDto.class);
        verify(restTemplateMock).postForObject(mockApi, request, CountryResponseDto.class);

        assertEquals(expectedCountryResponse, countryResponseDto);
    }

    @Test
    void invalidPostForObject() {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{", headers);
        when(restTemplateMock.postForObject(mockApi, request, String.class)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
        String response = ipApiBase.postForObject(ConfigConstants.IP_API_URL, request, String.class);
        assertNull(response);
    }

    @Test
    void getBasePath() {
        assertEquals(ConfigConstants.IP_API_URL, ipApiBase.getBasePath());
    }

    @Test
    void getRestTemplate() {
        assertEquals(restTemplateMock, ipApiBase.getRestTemplate());
    }
}