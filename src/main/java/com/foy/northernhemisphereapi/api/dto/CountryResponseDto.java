package com.foy.northernhemisphereapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryResponseDto {
    String status;
    String country;
    String countryCode;
    String region;
    String regionName;
    String city;
    String zip;
    String lat;
    String lon;
    String timezone;
    String isp;
    String org;
    String as;
    String message;
    String query;
}
