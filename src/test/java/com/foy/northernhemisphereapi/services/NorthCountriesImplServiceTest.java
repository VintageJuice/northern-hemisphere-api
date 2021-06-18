package com.foy.northernhemisphereapi.services;

import com.foy.northernhemisphereapi.NorthernHemisphereApiConfiguration;
import com.foy.northernhemisphereapi.api.dto.CountryResponseDto;
import com.foy.northernhemisphereapi.api.service.IpApiBatchService;
import com.foy.northernhemisphereapi.domain.Country;
import com.foy.northernhemisphereapi.domain.IpAddress;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {NorthCountriesImpl.class})
@ContextConfiguration(classes = NorthernHemisphereApiConfiguration.class)
class NorthCountriesImplServiceTest {

    private final List<IpAddress> duplicateRequestIps = List.of(
            new IpAddress("8.8.8.8"),
            new IpAddress("8.8.8.8")
    );
    private final List<IpAddress> alphabeticRequestIps = List.of(
            new IpAddress("8.8.8.8"),
            new IpAddress("1.179.112.0"),
            new IpAddress("3.5.252.5"),
            new IpAddress("1.0.16.5"),
            new IpAddress("2.18.168.8")
    );
    private final List<IpAddress> mixedHemisphereRequestIps = List.of(
            new IpAddress("201.8.8.8"),
            new IpAddress("61.80.152.201"),
            new IpAddress("2.22.149.0")
    );
    private final List<IpAddress> reservedRangeIp = List.of(
            new IpAddress("241.8.45.45")
    );
    private final List<CountryResponseDto> duplicateResponse = List.of(
            CountryResponseDto.builder().status("success").country("United States").lat("39.03").build(),
            CountryResponseDto.builder().status("success").country("United States").lat("39.03").build()
    );
    private final List<CountryResponseDto> alphabeticResponse = List.of(
            CountryResponseDto.builder().status("success").country("Austria").lat("48.1932").build(),
            CountryResponseDto.builder().status("success").country("Canada").lat("43.6532").build(),
            CountryResponseDto.builder().status("success").country("France").lat("48.8323").build(),
            CountryResponseDto.builder().status("success").country("Japan").lat("35.6978").build(),
            CountryResponseDto.builder().status("success").country("United States").lat("39.03").build()
    );
    private final List<CountryResponseDto> mixedHemisphereResponse = List.of(
            CountryResponseDto.builder().status("success").country("Brazil").lat("-23.5505").build(),
            CountryResponseDto.builder().status("success").country("South Korea").lat("33.453").build(),
            CountryResponseDto.builder().status("success").country("Argentina").lat("-34.6022").build()
    );

    private final List<CountryResponseDto> failResponse = Collections.emptyList();
    private final List<Country> expectedAlphabeticOrder = List.of(
            new Country("Austria"),
            new Country("Canada"),
            new Country("France"),
            new Country("Japan"),
            new Country("United States")
    );

    private final NorthCountriesImpl northCountriesImpl;

    @MockBean
    private IpApiBatchService ipApiBatchService;

    @Autowired
    NorthCountriesImplServiceTest(NorthCountriesImpl northCountriesImpl) {
        this.northCountriesImpl = northCountriesImpl;
    }

    @Test
    void getNorthCountriesWithDuplicateRequests() {
        when(ipApiBatchService.postBatchOfIps(any())).thenReturn(duplicateResponse);
        List<Country> result = northCountriesImpl.getNorthCountries(duplicateRequestIps);
        assertEquals(1, result.size());
        assertEquals("United States", result.get(0).getCountryName());
    }

    @Test
    void getNorthCountriesOnlyReturnsNorthernHemisphereCountries() {
        when(ipApiBatchService.postBatchOfIps(any())).thenReturn(mixedHemisphereResponse);
        List<Country> result = northCountriesImpl.getNorthCountries(mixedHemisphereRequestIps);
        assertEquals(1, result.size());
        assertEquals("South Korea", result.get(0).getCountryName());
    }

    @Test
    void getNorthCountriesWithFailStatus() {
        when(ipApiBatchService.postBatchOfIps(any())).thenReturn(failResponse);
        List<Country> result = northCountriesImpl.getNorthCountries(reservedRangeIp);
        assertEquals(0, result.size());
    }

    @Test
    void getNorthCountriesReturnsNorthernHemisphereCountriesAlphabetically() {
        when(ipApiBatchService.postBatchOfIps(any())).thenReturn(alphabeticResponse);
        List<Country> result = northCountriesImpl.getNorthCountries(alphabeticRequestIps);
        assertEquals(5, result.size());
        assertEquals(expectedAlphabeticOrder, result);
    }

    @Test
    void getAuthNorthCountriesWithDuplicateRequests() {
        when(ipApiBatchService.postBatchOfIps(any())).thenReturn(duplicateResponse);
        List<Country> result = northCountriesImpl.getAuthenticatedNorthCountries(duplicateRequestIps);
        assertEquals(1, result.size());
        assertEquals("United States", result.get(0).getCountryName());
    }

    @Test
    void getAuthNorthCountriesOnlyReturnsNorthernHemisphereCountries() {
        when(ipApiBatchService.postBatchOfIps(any())).thenReturn(mixedHemisphereResponse);
        List<Country> result = northCountriesImpl.getAuthenticatedNorthCountries(mixedHemisphereRequestIps);
        assertEquals(1, result.size());
        assertEquals("South Korea", result.get(0).getCountryName());
    }

    @Test
    void getAuthNorthCountriesWithFailStatus() {
        when(ipApiBatchService.postBatchOfIps(any())).thenReturn(failResponse);
        List<Country> result = northCountriesImpl.getAuthenticatedNorthCountries(reservedRangeIp);
        assertEquals(0, result.size());
    }

    @Test
    void getAuthNorthCountriesReturnsNorthernHemisphereCountriesAlphabetically() {
        when(ipApiBatchService.postBatchOfIps(any())).thenReturn(alphabeticResponse);
        List<Country> result = northCountriesImpl.getNorthCountries(alphabeticRequestIps);
        assertEquals(5, result.size());
        assertEquals(expectedAlphabeticOrder, result);
    }
}