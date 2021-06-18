package com.foy.northernhemisphereapi.services;

import com.foy.northernhemisphereapi.api.IpApi;
import com.foy.northernhemisphereapi.api.dto.CountryResponseDto;
import com.foy.northernhemisphereapi.api.dto.SingleQueryRequestDto;
import com.foy.northernhemisphereapi.api.service.IpApiBatchService;
import com.foy.northernhemisphereapi.domain.Country;
import com.foy.northernhemisphereapi.domain.IpAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NorthCountriesImpl implements NorthCountriesService {

    private final IpApiBatchService ipApiBatchService;

    @Autowired
    public NorthCountriesImpl(IpApi ipApi) {
        this.ipApiBatchService = ipApi.getIpApiBatchService();
    }

    /**
     * Calls the IpApi to process a batch of ip addresses and return any countries from the Northern Hemisphere.
     * @param ipAddresses - The List of Domain Object IpAddresses.
     * @return - The List of Domain Object Countries - distinct and alphabetized.
     */
    public List<Country> getNorthCountries(List<IpAddress> ipAddresses) {
        List<SingleQueryRequestDto> queryList = generateRequests(ipAddresses);
        List<CountryResponseDto> response = ipApiBatchService.postBatchOfIps(queryList);
        return distinctSortedCountries(response);
    }

    /**
     * Identical to getNorthCountries(List of IpAddress ipAddresses), wrapper for authenticated endpoint with identical behaviour.
     * @param ipAddresses - The List of Domain Object IpAddresses.
     * @return - The List of Domain Object Countries - distinct and alphabetized.
     */
    public List<Country> getAuthenticatedNorthCountries(List<IpAddress> ipAddresses) {
        return getNorthCountries(ipAddresses);
    }

    /**
     * Builds IpApi SingleQueryRequestDto Objects out of Domain IpAddress Lists
     * @param ipAddresses - The List of Domain IpAddresses
     * @return - A List of IpApi Transfer Objects
     */
    private List<SingleQueryRequestDto> generateRequests(List<IpAddress> ipAddresses) {
        return ipAddresses.stream().map(this::createSingleQueryRequestDto).collect(Collectors.toList());
    }

    /**
     * Constructs a SingleQueryRequestDto from a Domain IpAddress
     * @param ip - The ip to be transformed to dto format.
     * @return - The SingleQueryRequestDto object.
     */
    private SingleQueryRequestDto createSingleQueryRequestDto(IpAddress ip) {
        return SingleQueryRequestDto.builder().query(ip.getIp()).build();
    }

    /**
     * Sorts a List of IpApi CountryResponseDto objects into alphabetic order,
     * whilst removing any responses that returned as a failure due to being reserved ips, private etc,
     * and ensures that only distinct Country Domain Objects are returned in a List.
     * @param response - A List of CountryResponseDto Objects from IpApi, possibly unsorted and containing duplicates.
     * @return - A List of Distinct, Alphabetised Domain Country Objects.
     */
    private List<Country> distinctSortedCountries(List<CountryResponseDto> response) {
        return response.stream()
                .filter(this::isNorthernHemisphere)
                .map(CountryResponseDto::getCountry)
                .map(Country::new)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Determines if a CountryResponseDto is from the NorthernHemisphere.
     * @param country - The IpApi CountryResponseDto object.
     * @return - true/false.
     */
    private boolean isNorthernHemisphere(CountryResponseDto country) {
        return Float.parseFloat(country.getLat()) > 0;
    }
}
