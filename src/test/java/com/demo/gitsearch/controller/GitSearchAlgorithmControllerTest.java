package com.demo.gitsearch.controller;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.demo.gitsearch.models.GitPopularityResponse;
import com.demo.gitsearch.service.GitSearchService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
class GitSearchAlgorithmControllerTest {

	GitSearchAlgorithmController gitSearchAlgorithmController;
	ObjectMapper objectMapper = new ObjectMapper();

	@Mock
	GitSearchService gitSearchService;

	@BeforeEach
	public void setUp() {
		gitSearchAlgorithmController = new GitSearchAlgorithmController(gitSearchService);
	}

	@SneakyThrows
	@Test
	void testFindRepositoryScoreSuccess() {
		var searchResponse = ResponseEntity.ok(List
				.of(GitPopularityResponse.builder().fullName("test").popularity(BigDecimal.valueOf(10.2458)).build()));
		when(gitSearchService.assesPopularityScore(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(Mono.just(searchResponse));
		Mono<ResponseEntity<List<GitPopularityResponse>>> response = gitSearchAlgorithmController
				.findRepositoryScore("gitSearchExample", "java");
		StepVerifier.create(response).assertNext(scores -> {
			Assertions.assertEquals(HttpStatus.OK, scores.getStatusCode());
			Assertions.assertEquals(1, scores.getBody().size());
			Assertions.assertEquals(BigDecimal.valueOf(10.2458), scores.getBody().get(0).getPopularity());
		}).verifyComplete();

	}

	@SneakyThrows
	@Test
	void testFindRepositoryScoreNotFound() {
		when(gitSearchService.assesPopularityScore(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(Mono.just(ResponseEntity.notFound().build()));
		Mono<ResponseEntity<List<GitPopularityResponse>>> response = gitSearchAlgorithmController
				.findRepositoryScore("gitSearchExample", "java");
		StepVerifier.create(response).assertNext(scores -> {
			Assertions.assertEquals(HttpStatus.NOT_FOUND, scores.getStatusCode());
		}).verifyComplete();

	}

	@SneakyThrows
	@Test
	void testFindRepositoryScoreException() {
		when(gitSearchService.assesPopularityScore(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(Mono.just(ResponseEntity.internalServerError().build()));
		Mono<ResponseEntity<List<GitPopularityResponse>>> response = gitSearchAlgorithmController
				.findRepositoryScore("gitSearchExample", "java");
		StepVerifier.create(response).assertNext(scores -> {
			Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, scores.getStatusCode());
		}).verifyComplete();

	}

}
