package com.foy.northernhemisphereapi.api.service;

import com.foy.northernhemisphereapi.api.dto.CountryResponseDto;
import com.foy.northernhemisphereapi.api.dto.SingleQueryRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class IpApiBatchService extends IpApiBase {

    public IpApiBatchService(String basePath, RestTemplate restTemplate) {
        super(basePath, restTemplate);
    }

    /**
     * Posts a list of queries to the IpApi Batch endpoint as Json.
     * Transform plain jsonArray response into Domain Format, logs any errors, and passes back successful entries
     * @param queryList - A List of SingleQueryRequestDto Objects.
     * @return - A List of CountryResponseDto Objects.
     */
    public List<CountryResponseDto> postBatchOfIps(List<SingleQueryRequestDto> queryList) {
        log.debug("calling ip api with requested ips...");
        HttpEntity<List<SingleQueryRequestDto>> request = generateRequestWithHeaders(queryList);
        CountryResponseDto[] response = postForObject(getBatchBasePath(), request, CountryResponseDto[].class);
        List<CountryResponseDto> countryResponseDtos = Arrays.stream(response).collect(Collectors.toList());
        logResponseFailures(countryResponseDtos);
        return countryResponseDtos.stream().filter(this::isSuccessStatus).collect(Collectors.toList());
    }

    /**
     * Constructs headers to specify Json Media Type, and then constructs and returns a new HttpEntity request configured with them.
     * @param queryList - The List of SingleQueryRequestDto Objects to be executed in the request.
     * @return - The configured HttpEntity ready for execution.
     */
    private HttpEntity<List<SingleQueryRequestDto>> generateRequestWithHeaders(List<SingleQueryRequestDto> queryList) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(queryList, headers);
    }

    /**
     * Returns the base path of the batch endpoint on the IpApi.
     * @return - the path.
     */
    private String getBatchBasePath() {
        return getBasePath() + "/batch";
    }

    /**
     * Logs failure responses from IpApi.
     * @param countryResponseDtos - The List of CountryResponseDto Objects from IpApi response to process for failures.
     */
    private void logResponseFailures(List<CountryResponseDto> countryResponseDtos) {
        List<CountryResponseDto> failedResponses = getFailedResponses(countryResponseDtos);
        failedResponses.forEach(this::logWithFailureInformation);
    }

    /**
     * Processes a List of CountryResponseDto Objects from IpApi and returns a list of Failure Responses.
     * @param countryResponseDtos - The List of CountryResponseDto Objects from IpApi response to process for failures.
     * @return - The List of Failure Entries to log.
     */
    private List<CountryResponseDto> getFailedResponses(List<CountryResponseDto> countryResponseDtos) {
        return countryResponseDtos.stream().filter(this::isFailureStatus).collect(Collectors.toList());
    }

    /**
     * Determines if a CountryResponseDto was a failure
     * @param countryResponseDto - The CountryResponseDto.
     * @return - true/false
     */
    private boolean isFailureStatus(CountryResponseDto countryResponseDto) {
        return countryResponseDto.getStatus().contentEquals("fail");
    }

    /**
     * Determines if a CountryResponseDto was a success
     * @param countryResponseDto - The CountryResponseDto.
     * @return - true/false
     */
    private boolean isSuccessStatus(CountryResponseDto countryResponseDto) {
        return countryResponseDto.getStatus().contentEquals("success");
    }

    /**
     * Logs a failed response from the IpApi at error level.
     * @param failedIpResponse - The failed CountryResponseDto to log to error.
     */
    private void logWithFailureInformation(CountryResponseDto failedIpResponse) {
        log.error("Query: " + failedIpResponse.getQuery() + " failed with message: " + failedIpResponse.getMessage());
    }
}
