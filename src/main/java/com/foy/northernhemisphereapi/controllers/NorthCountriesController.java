package com.foy.northernhemisphereapi.controllers;

import com.foy.northernhemisphereapi.domain.IpAddress;
import com.foy.northernhemisphereapi.dto.NorthCountriesRequestDto;
import com.foy.northernhemisphereapi.dto.NorthCountriesResponseDto;
import com.foy.northernhemisphereapi.services.NorthCountriesService;
import com.foy.northernhemisphereapi.utils.ConfigConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

import static com.foy.northernhemisphereapi.utils.NorthCountriesHemisphereApiMapper.convertToIpList;
import static com.foy.northernhemisphereapi.utils.NorthCountriesHemisphereApiMapper.convertToNorthCountriesResponseDto;

@RestController
@RequestMapping(path = "northcountries", produces = "application/json")
@Validated
@Slf4j
public class NorthCountriesController {

    private final NorthCountriesService northCountriesService;

    @Autowired
    public NorthCountriesController(NorthCountriesService northCountriesService) {
        this.northCountriesService = northCountriesService;
    }

    /**
     * Rest API GET request Handler for unauthenticated northern hemisphere ip requests.
     * Requests are monitored by a default in memory cache implementation.
     * @param ipAddresses - A Collection of ip addresses, that must be properly formatted as ip=n.n.n.n
     *                      With a minimum of one ip address, and a maximum of five.
     * @return Json formatted array containing distinct alphabetized countries names from the Northern Hemisphere.
     *         Note that Southern Hemisphere countries will not be returned.
     */
    @Cacheable(ConfigConstants.IP_CACHE_NAME)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public NorthCountriesResponseDto getNorthCountries(
                                               @Size(min = 1, max = 5, message = "you must provide at least one ip address, up to a maximum of five.")
                                               @NotEmpty
                                               @Valid
                                               @RequestParam(value = "ip")
                                               List<NorthCountriesRequestDto> ipAddresses) {
        log.debug("retrieving northern hemisphere countries by ip...");
        log.debug("request parameters: " + ipAddresses);
        List<IpAddress> ipAddressList = convertToIpList(ipAddresses);
        return convertToNorthCountriesResponseDto(northCountriesService.getNorthCountries(ipAddressList));
    }
}
