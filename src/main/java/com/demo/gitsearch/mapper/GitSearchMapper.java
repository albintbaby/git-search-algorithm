package com.demo.gitsearch.mapper;

import com.demo.gitsearch.models.GitPopularityResponse;
import com.demo.gitsearch.models.GitSearchResponse;
import lombok.experimental.UtilityClass;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static com.demo.gitsearch.constants.GitSearchApplicationConstants.*;

/**
 * Mapper class used to create request and response objects for git search service
 */

@UtilityClass
public class GitSearchMapper {

    /**
     * Create git search query based on input parameters
     *
     * @param repository - Search query for the repository
     * @param language   - Programming language for the repository
     * @return search query for git repository
     */
    public String createSearchQuery(String repository, String language) {
        return repository +
                "+" +
                "language:" +
                language +
                "&sort=stars&order=desc";
    }

    /**
     * Map to Git popularity response object based on the Git search response
     * And sort it based on popularity descending order
     *
     * @param gitSearchResponse - Search response received from git
     * @return popularity response objects
     */
    public Mono<List<GitPopularityResponse>> mapPopularityScore(GitSearchResponse gitSearchResponse) {

        return Mono.just(gitSearchResponse)
                .filter(gitSearch -> gitSearch.getTotalCount() > 0)
                .map(gitSearch -> gitSearch.getItems().stream().map(gitItem -> GitPopularityResponse.builder()
                                .popularity(calculatePopularityScore(gitItem.getStargazersCount(), gitItem.getForks(), gitItem.getUpdatedAt()))
                                .forks(gitItem.getForksCount())
                                .stars(gitItem.getStargazersCount())
                                .gitUrl(gitItem.getGitUrl())
                                .name(gitItem.getName())
                                .fullName(gitItem.getName())
                                .lastUpdated(gitItem.getUpdatedAt())
                                .build()).toList()
                        .stream().sorted(Comparator.comparing(GitPopularityResponse::getPopularity))
                        .toList().reversed()
                );

    }

    /**
     * Calculate the popularity score received for the git repository based on input parameters
     *
     * @param stars       - stars received for git repository
     * @param forks       - forks created from git repository
     * @param lastUpdated - last updated date for the git repository
     * @return popularity score calculated for the repository
     */
    private BigDecimal calculatePopularityScore(int stars, int forks, Date lastUpdated) {
        BigDecimal starsScore = BigDecimal.valueOf(stars * STARS_WEIGHT).setScale(4, RoundingMode.HALF_UP);
        BigDecimal forksScore = BigDecimal.valueOf(forks * FORKS_WEIGHT).setScale(4, RoundingMode.HALF_UP);
        BigDecimal recencyScore = BigDecimal.ZERO;
        if (lastUpdated != null) {
            LocalDateTime date = lastUpdated.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            long daysSinceLastUpdate = Duration.between(date, LocalDateTime.now()).toDays();
            recencyScore = BigDecimal.valueOf(RECENCY_WEIGHT / (daysSinceLastUpdate + 1)).setScale(4, RoundingMode.HALF_UP);
        }

        return starsScore.add(forksScore).add(recencyScore).setScale(4, RoundingMode.HALF_UP);
    }
}
