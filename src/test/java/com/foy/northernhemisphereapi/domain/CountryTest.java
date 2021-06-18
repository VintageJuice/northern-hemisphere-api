package com.foy.northernhemisphereapi.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CountryTest {

    @Test
    void builder() {
        String testValue = "country";
        Country country = Country.builder()
                .countryName(testValue)
                .build();
        assertEquals(testValue, country.getCountryName());
    }

    @Test
    void testData() {
        Country country = new Country();
        country.setCountryName("albania");
        assertEquals("albania", country.getCountryName());
    }

    @Test
    void testAlphabeticalComparison() {
        Country zimbabwe = new Country("Zimbabwe");
        Country canada = new Country("Canada");
        Country canadaDuplicate = new Country("Canada");
        Country argentina = new Country("Argentina");

        assertEquals(0, canada.compareTo(canadaDuplicate));
        assertTrue(zimbabwe.compareTo(argentina) > 0);
        assertTrue(canada.compareTo(zimbabwe) < 0);
        assertTrue(canada.compareTo(argentina) > 0);
    }
}
