package com.demo.gitsearch.adapter;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;

import com.demo.gitsearch.models.GitSearchResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class GitSearchApiAdapterServiceTest {

	GitSearchApiAdapterService gitSearchApiAdapterService;

	MockWebServer mockWebServer;
	ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void setUp() throws IOException {
		mockWebServer = new MockWebServer();
		mockWebServer.start();
		gitSearchApiAdapterService = new GitSearchApiAdapterService();
		ReflectionTestUtils.setField(gitSearchApiAdapterService, "gitHost", "http://localhost:"+mockWebServer.getPort());
		ReflectionTestUtils.setField(gitSearchApiAdapterService, "gitSearchResource", "/search/repositories");

	}

	@AfterEach
	public void tearDown() throws IOException {
		mockWebServer.shutdown();
	}

	@SneakyThrows
	@Test
	void testSearch() {
		URL gitSearchResponse200 = this.getClass().getClassLoader().getResource("search-response-200.json");
		GitSearchResponse gitSearchResponse = objectMapper.readValue(new File(gitSearchResponse200.getPath()),
				GitSearchResponse.class);
		mockWebServer.enqueue(new MockResponse().setResponseCode(HttpStatus.OK.value())
				.setBody(objectMapper.writeValueAsString(gitSearchResponse))
				.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
		Mono<GitSearchResponse> response = gitSearchApiAdapterService.search("query");
		StepVerifier.create(response).assertNext(searches -> {
			Assertions.assertEquals(2, searches.totalCount);
			Assertions.assertEquals(2, searches.getItems().size());
		}).verifyComplete();

	}
	
	@SneakyThrows
	@Test
	void testSearchNoResults() {
		URL gitSearchResponse404 = this.getClass().getClassLoader().getResource("search-response-404.json");
		GitSearchResponse gitSearchResponse = objectMapper.readValue(new File(gitSearchResponse404.getPath()),
				GitSearchResponse.class);
		mockWebServer.enqueue(new MockResponse().setResponseCode(HttpStatus.OK.value())
				.setBody(objectMapper.writeValueAsString(gitSearchResponse))
				.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
		Mono<GitSearchResponse> response = gitSearchApiAdapterService.search("query");
		StepVerifier.create(response).assertNext(searches -> {
			Assertions.assertEquals(0, searches.totalCount);
			Assertions.assertEquals(0, searches.getItems().size());
		}).verifyComplete();

	}
	
	@SneakyThrows
	@Test
	void testSearchException() {
		mockWebServer.enqueue(new MockResponse().setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
		Mono<GitSearchResponse> response = gitSearchApiAdapterService.search("query");
		StepVerifier.create(response).expectError().verify();
	}
}
