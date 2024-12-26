package com.demo.gitsearch.service;

import com.demo.gitsearch.models.GitPopularityResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.List;

public interface GitSearchService {
    Mono<ResponseEntity<List<GitPopularityResponse>>> assesPopularityScore(String repository, String language);
}
