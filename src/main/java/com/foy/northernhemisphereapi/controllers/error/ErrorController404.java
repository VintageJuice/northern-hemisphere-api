package com.foy.northernhemisphereapi.controllers.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController404 implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        return "error/404";
    }
}
