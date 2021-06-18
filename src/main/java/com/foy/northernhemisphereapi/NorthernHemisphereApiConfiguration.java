package com.foy.northernhemisphereapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.foy.northernhemisphereapi.api.config.IpApiServicesConfig;
import com.foy.northernhemisphereapi.config.SecurityConfiguration;
import com.foy.northernhemisphereapi.config.SpringFoxConfig;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
@Import({SecurityConfiguration.class, SpringFoxConfig.class, IpApiServicesConfig.class})
public class NorthernHemisphereApiConfiguration {

    @Bean
    @Primary
    public ObjectMapper mapper(Jackson2ObjectMapperBuilder builder) {
        var objectMapper = builder.build();
        objectMapper.registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
