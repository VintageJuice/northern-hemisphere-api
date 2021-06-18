package com.foy.northernhemisphereapi.api;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.foy.northernhemisphereapi.api.dto.CountryResponseDto;
import com.foy.northernhemisphereapi.api.dto.SingleQueryRequestDto;
import com.foy.northernhemisphereapi.utils.LogBackMemoryAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IpApiBatchServiceTest {

    private final IpApi ipApi;
    private LogBackMemoryAppender logBackMemoryAppender;

    @Autowired
    IpApiBatchServiceTest(IpApi ipApi) {
        this.ipApi = ipApi;
    }

    @BeforeEach
    void setup() {
        Logger logger = (Logger) LoggerFactory.getLogger("com.foy.northernhemisphereapi");
        logBackMemoryAppender = new LogBackMemoryAppender(logger);
    }

    @AfterEach()
    void tearDown() {
        logBackMemoryAppender.stop();
    }

    @Test
    void postSingleQueryByBatchIps() {
        List<SingleQueryRequestDto> queryList = List.of(new SingleQueryRequestDto("8.8.8.8", "", ""));
        var response = ipApi.getIpApiBatchService().postBatchOfIps(queryList);
        assertNotNull(response);
        assertTrue(response.stream().anyMatch(countryResponseDto -> countryResponseDto.getLon().equals("-77.5")));
    }

    @Test
    void postSingleFailingQueryByBatchIps() {
        List<SingleQueryRequestDto> queryList = List.of(new SingleQueryRequestDto("241.8.45.45", "", ""));
        var response = ipApi.getIpApiBatchService().postBatchOfIps(queryList);
        assertNotNull(response);
        assertTrue(response.stream().noneMatch(this::isReservedRange));
        assertTrue(logBackMemoryAppender.contains("Query: 241.8.45.45 failed with message: reserved range", Level.ERROR)
        );
    }

    private boolean isReservedRange(CountryResponseDto countryResponseDto) {
        return countryResponseDto.getMessage().equals("reserved range");
    }

    @Test
    void postMultipleQueriesByBatchIps() {
        String expectedCityBradleyStoke = "Bradley Stoke";
        String expectedCityVerdellino = "Verdellino";
        List<SingleQueryRequestDto> queryList = List.of(
                new SingleQueryRequestDto("31.80.152.201", "", ""),
                new SingleQueryRequestDto("91.80.152.201", "", "")
        );

        var response = ipApi.getIpApiBatchService().postBatchOfIps(queryList);
        assertNotNull(response, "response from postMultipleQueriesByBatchIps was null!");
        assertEquals(queryList.size(), response.size(), "expected cities size and response cities size differ!");
        assertEquals(expectedCityBradleyStoke, response.get(0).getCity(), "expected to get " + expectedCityBradleyStoke + " but did not.");
        assertEquals(expectedCityVerdellino, response.get(1).getCity(), "expected to get " + expectedCityVerdellino + " but did not.");
    }
}

