package com.demo.gitsearch.service;

import static org.mockito.Mockito.when;

import java.io.File;
import java.net.URL;
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

import com.demo.gitsearch.adapter.GitSearchApiAdapterService;
import com.demo.gitsearch.models.GitPopularityResponse;
import com.demo.gitsearch.models.GitSearchResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
class GitSearchServiceTest {

	GitSearchService gitSearchService;

	@Mock
	GitSearchApiAdapterService gitSearchApiAdapterService;

	ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void setUp() throws Exception {
		gitSearchService = new GitSearchServiceImpl(gitSearchApiAdapterService);

	}

	@SneakyThrows
	@Test
	void testAssesPopularityScoreSuccess() {
		URL gitSearchResponse200 = this.getClass().getClassLoader().getResource("search-response-200.json");
		GitSearchResponse gitSearchResponse = objectMapper.readValue(new File(gitSearchResponse200.getPath()),
				GitSearchResponse.class);
		when(gitSearchApiAdapterService.search(Mockito.anyString())).thenReturn(Mono.just(gitSearchResponse));
		Mono<ResponseEntity<List<GitPopularityResponse>>> response = gitSearchService
				.assesPopularityScore("gitSearchExample", "java");
		StepVerifier.create(response).assertNext(scores -> {
			Assertions.assertEquals(HttpStatus.OK, scores.getStatusCode());
			Assertions.assertEquals(2, scores.getBody().size());
		}).verifyComplete();

	}
	
	@SneakyThrows
	@Test
	void testAssesPopularityScoreNotFound() {
		URL gitSearchResponse404 = this.getClass().getClassLoader().getResource("search-response-404.json");
		GitSearchResponse gitSearchResponse = objectMapper.readValue(new File(gitSearchResponse404.getPath()),
				GitSearchResponse.class);
		when(gitSearchApiAdapterService.search(Mockito.anyString())).thenReturn(Mono.just(gitSearchResponse));
		Mono<ResponseEntity<List<GitPopularityResponse>>> response = gitSearchService
				.assesPopularityScore("gitSearchExample", "java");
		StepVerifier.create(response).assertNext(scores -> {
			Assertions.assertEquals(HttpStatus.NOT_FOUND, scores.getStatusCode());
		}).verifyComplete();

	}
	
	@SneakyThrows
	@Test
	void testAssesPopularityScoreException() {
		when(gitSearchApiAdapterService.search(Mockito.anyString())).thenReturn(Mono.error(new NullPointerException()));
		Mono<ResponseEntity<List<GitPopularityResponse>>> response = gitSearchService
				.assesPopularityScore("gitSearchExample", "java");
		StepVerifier.create(response).assertNext(scores -> {
			Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, scores.getStatusCode());
		}).verifyComplete();

	}

}
