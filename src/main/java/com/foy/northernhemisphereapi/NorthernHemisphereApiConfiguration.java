package com.foy.northernhemisphereapi;

import com.foy.northernhemisphereapi.config.SecurityConfiguration;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootConfiguration
@Import({SecurityConfiguration.class})
public class NorthernHemisphereApiConfiguration {
}
