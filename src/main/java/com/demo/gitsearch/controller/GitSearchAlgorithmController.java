package com.demo.gitsearch.controller;

import com.demo.gitsearch.models.GitPopularityResponse;
import com.demo.gitsearch.service.GitSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Controller class to create REST APIS for GIT related operations
 */

@RestController
@RequestMapping("/git")
@Slf4j
@RequiredArgsConstructor
public class GitSearchAlgorithmController {

    private final GitSearchService gitSearchService;

    /**
     * Rest GET API to find the scoring for the repository based on search query like repository name and language.
     * 
     * @param repository - Search query for the repository
     * @param language   - Programming language for the repository
     * @return - Response entity object with popularity score
     */
    @GetMapping("/search")
    public Mono<ResponseEntity<List<GitPopularityResponse>>> findRepositoryScore(@RequestParam String repository, @RequestParam String language) {
        return gitSearchService.assesPopularityScore(repository, language);
    }
}
