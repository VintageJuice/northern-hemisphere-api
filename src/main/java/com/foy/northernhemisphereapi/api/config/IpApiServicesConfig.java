package com.foy.northernhemisphereapi.api.config;

import com.foy.northernhemisphereapi.api.IpApi;
import com.foy.northernhemisphereapi.api.IpApiServices;
import com.foy.northernhemisphereapi.api.service.IpApiBase;
import com.foy.northernhemisphereapi.api.service.IpApiBatchService;
import com.foy.northernhemisphereapi.utils.ConfigConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class IpApiServicesConfig {

    @Bean
    String basePath() {
        return ConfigConstants.IP_API_URL;
    }

    @Bean
    RestTemplate restTemplate() {
        var restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        return restTemplate;
    }

    @Bean
    IpApiBase ipApiBase() {
        return new IpApiBase(basePath(), restTemplate());
    }

    @Bean
    IpApiBatchService ipApiBatchService() {
        return new IpApiBatchService(basePath(), restTemplate());
    }

    @Bean
    IpApi ipApi() {
        return new IpApiServices(ipApiBatchService());
    }
}
