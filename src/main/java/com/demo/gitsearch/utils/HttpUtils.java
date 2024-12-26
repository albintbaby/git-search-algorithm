package com.demo.gitsearch.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * HTTP util class for create URI components and HTTP related operations
 */

@UtilityClass
@Slf4j
public class HttpUtils {

    /**
     * Create a URI object for Git HTTP request
     *
     * @param apiHost         - the URL for API host
     * @param apiUrl          - the URL for the HTTP service
     * @param queryParameters - query parameters if any
     * @return the URI object
     */
    public URI createUriComponentBuilder(String apiHost, String apiUrl, MultiValueMap<String, String> queryParameters) {
        return UriComponentsBuilder
                .fromUriString(apiHost + apiUrl)
                .queryParams(queryParameters)
                .build().toUri();
    }
}
