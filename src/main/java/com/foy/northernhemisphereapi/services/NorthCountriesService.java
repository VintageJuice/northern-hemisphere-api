package com.foy.northernhemisphereapi.services;

import com.foy.northernhemisphereapi.domain.Country;
import com.foy.northernhemisphereapi.domain.IpAddress;

import java.util.List;

public interface NorthCountriesService {
    List<Country> getNorthCountries(List<IpAddress> params);
    List<Country> getAuthenticatedNorthCountries(List<IpAddress> params);
}
