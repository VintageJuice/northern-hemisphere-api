package com.foy.northernhemisphereapi.api.service;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Objects;

@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
@Slf4j
public class IpApiBase {

    private final String basePath;
    private final RestTemplate restTemplate;

    @Autowired
    public IpApiBase(String basePath, RestTemplate restTemplate) {
        this.basePath = basePath;
        this.restTemplate = restTemplate;
    }

    /**
     * Posts a request to the URL defined, with the request supplied and maps it to the class type specified.
     * If response to the request is null, and cannot map to an appropriate POJO a Client Exception is thrown.
     * @param url - The URL to call.
     * @param request - The request to send.
     * @param responseType - The POJO class type to map to.
     * @param <T> - The Generic return Type desired.
     * @return - A response Object from the Server requested.
     */
    <T> T postForObject(String url, Object request, Class<T> responseType) {
        try {
            var response = restTemplate.postForObject(url, request, responseType);
            if (Objects.isNull(response)) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "request: " + request + " to IpApi resulted in null response, was your request properly formatted?");
            }
            return response;
        }
        catch (HttpClientErrorException httpClientErrorException) {
            log.error(httpClientErrorException.getMessage());
            log.error(Arrays.toString(httpClientErrorException.getStackTrace()));
            return null;
        }
    }
}
