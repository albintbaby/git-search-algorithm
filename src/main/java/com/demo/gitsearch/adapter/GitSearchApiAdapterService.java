package com.demo.gitsearch.adapter;


import com.demo.gitsearch.models.GitSearchResponse;
import com.demo.gitsearch.utils.HttpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

import static com.demo.gitsearch.constants.GitSearchApplicationMessages.*;

/**
 * Adapter class to call external GIT API for searching repository based on given input
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class GitSearchApiAdapterService {

    WebClient webClient = WebClient.builder().build();

    @Value("${spring.application.git.host}")
    private String gitHost;

    @Value("${spring.application.git.search-resource}")
    private String gitSearchResource;


    /**
     * Method to call GIT search API to find the repositories based on given query
     *
     * @param query - search query to GIT
     * @return - Response from GIT
     */
    public Mono<GitSearchResponse> search(String query) {
        URI uri = HttpUtils.createUriComponentBuilder(gitHost, gitSearchResource, buildQueryParameters(query));
        log.info(LOG_SENDING_GET_REQUEST_MESSAGE, uri, query);
        return webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(GitSearchResponse.class)
                .timeout(Duration.ofSeconds(45))
                .doOnSuccess(gitSearchResponse -> log.info(LOG_RECEIVED_SUCCESSFUL_RESPONSE_MESSAGE, gitSearchResponse))
                .doOnError(throwable -> log.error(LOG_RECEIVED_ERROR_RESPONSE_MESSAGE, uri, throwable.getMessage(), throwable));
    }

    private MultiValueMap<String, String> buildQueryParameters(String query) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("q", query);
        return params;
    }
}
