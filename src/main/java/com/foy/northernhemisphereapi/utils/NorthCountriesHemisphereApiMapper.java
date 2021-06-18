package com.foy.northernhemisphereapi.utils;

import com.foy.northernhemisphereapi.domain.Country;
import com.foy.northernhemisphereapi.domain.IpAddress;
import com.foy.northernhemisphereapi.dto.NorthCountriesRequestDto;
import com.foy.northernhemisphereapi.dto.NorthCountriesResponseDto;
import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class NorthCountriesHemisphereApiMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    private NorthCountriesHemisphereApiMapper(){}

    /**
     * Converts Country Domain Objects into their related Country Data Transfer Objects.
     * @param countries - A list of Domain Country Objects to be converted.
     * @return - A NorthCountriesResponseDto, which contains a list of country names for transport.
     */
    public static NorthCountriesResponseDto convertToNorthCountriesResponseDto(List<Country> countries) {
        var northCountriesResponseDto = NorthCountriesResponseDto.builder().build();
        List<String> countryNames = countries.stream()
                .map(NorthCountriesHemisphereApiMapper::convertToString)
                .collect(Collectors.toList());
        northCountriesResponseDto.setNorthCountries(countryNames);
        return northCountriesResponseDto;
    }

    /**
     * Converts an individual Country Domain object to a String.
     * @param country - A Country Domain Object.
     * @return - The Country Name as a String.
     */
    private static String convertToString(Country country) {
        return country.getCountryName();
    }

    /**
     * Converts NorthCountriesRequestDtos, which contain ip addresses into their Domain representation as a List of IpAddresses.
     * @param ipAddresses - The ipAddresses in Data Transfer Object format.
     * @return - The Domain representation, a list of IpAddresses.
     */
    public static List<IpAddress> convertToIpList(Collection<NorthCountriesRequestDto> ipAddresses) {
        return ipAddresses.stream()
                .map(NorthCountriesHemisphereApiMapper::convertToIpAddress)
                .collect(Collectors.toList());
    }

    /**
     * Converts an individual NorthCountriesRequestDto Object into a Domain IpAddress.
     * @param ipRequestDto - The NorthCountriesRequestDto Object.
     * @return - The domain IpAddress Object.
     */
    private static IpAddress convertToIpAddress(NorthCountriesRequestDto ipRequestDto) {
        return modelMapper.map(ipRequestDto, IpAddress.class);
    }
}
