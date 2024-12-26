package com.demo.gitsearch.service;

import com.demo.gitsearch.adapter.GitSearchApiAdapterService;
import com.demo.gitsearch.mapper.GitSearchMapper;
import com.demo.gitsearch.models.GitPopularityResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.demo.gitsearch.constants.GitSearchApplicationMessages.LOG_ERROR_MAPPING_GIT_RESPONSE_MESSAGE;
import static com.demo.gitsearch.constants.GitSearchApplicationMessages.LOG_MAPPED_POPULARITY_SCORE_RESPONSE_MESSAGE;

/**
 * Service class to perform business logic for GIT search and map based on popularity
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class GitSearchServiceImpl implements GitSearchService {

    private final GitSearchApiAdapterService gitSearchApiAdapterService;

    /**
     * Service method asses the popularity score for GIT repository based on stars, forks and date updated
     * with input parameters containing repository name and programming language for the repository
     *
     * @param repository - Search query for the repository
     * @param language   - Programming language for the repository
     * @return - Response entity object with popularity score
     */
    @Override
    public Mono<ResponseEntity<List<GitPopularityResponse>>> assesPopularityScore(String repository, String language) {
        return gitSearchApiAdapterService.search(GitSearchMapper.createSearchQuery(repository, language))
                .flatMap(GitSearchMapper::mapPopularityScore)
                .doOnNext(gitPopularityResponses -> log.info(LOG_MAPPED_POPULARITY_SCORE_RESPONSE_MESSAGE, repository, language, gitPopularityResponses))
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
                .doOnError(throwable -> log.error(LOG_ERROR_MAPPING_GIT_RESPONSE_MESSAGE, throwable.getMessage(), throwable))
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }
}
