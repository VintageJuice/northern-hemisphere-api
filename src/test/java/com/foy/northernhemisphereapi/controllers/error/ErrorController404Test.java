package com.foy.northernhemisphereapi.controllers.error;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorController404Test {

    @Test
    void handleError() {
        ErrorController404 errorController404 = new ErrorController404();
        assertEquals("error/404", errorController404.handleError());
    }
}