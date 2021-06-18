package com.foy.northernhemisphereapi.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IpAddressTest {

    @Test
    void builder() {
        String testStub = "testIp";
        IpAddress ipAddress = IpAddress.builder().ip(testStub).build();
        assertEquals(testStub, ipAddress.getIp());
    }
}