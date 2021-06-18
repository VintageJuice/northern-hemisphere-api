package com.foy.northernhemisphereapi;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.Mockito.mockStatic;

@SpringBootTest
@ContextConfiguration(classes = NorthernHemisphereApiConfiguration.class)
class NorthernHemisphereApiApplicationTest {

    @Test
    void contextLoads() {
        try (MockedStatic<SpringApplication> mockApplication = mockStatic(SpringApplication.class)) {

            mockApplication
                .when(this::getConfigurableApplicationContext)
                .thenReturn(Mockito.mock(ConfigurableApplicationContext.class));

            NorthernHemisphereApiApplication.main(new String[] { "foo", "bar" });

            mockApplication.verify(this::getConfigurableApplicationContext);

        }
    }

    private void getConfigurableApplicationContext() {
        SpringApplication.run(NorthernHemisphereApiApplication.class, "foo", "bar");
    }

}
