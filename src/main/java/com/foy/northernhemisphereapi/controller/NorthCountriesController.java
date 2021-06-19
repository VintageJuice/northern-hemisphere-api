package com.foy.northernhemisphereapi.controller;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("northcountries")
@TestConfiguration
public class NorthCountriesController {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = "application/json")
    public String getNorthCountries() {
        return "hello north countries!";
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/login", produces = "application/json")
    public String getNorthCountriesLogin() {
        return "hello north countries login!";
    }
}
