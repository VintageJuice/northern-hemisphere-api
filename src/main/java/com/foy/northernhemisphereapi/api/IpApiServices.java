package com.foy.northernhemisphereapi.api;

import com.foy.northernhemisphereapi.api.service.IpApiBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IpApiServices implements IpApi {

    private final IpApiBatchService ipApiBatchService;

    @Autowired
    public IpApiServices(IpApiBatchService ipApiBatchService) {
        this.ipApiBatchService = ipApiBatchService;
    }

    @Override
    public IpApiBatchService getIpApiBatchService() {
        return ipApiBatchService;
    }
}
