package com.foy.northernhemisphereapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Country implements Comparable<Country> {
    private String countryName;

    @Override
    public int compareTo(Country country) {
        return this.getCountryName().compareToIgnoreCase(country.getCountryName());
    }
}
