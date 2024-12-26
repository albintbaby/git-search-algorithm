package com.demo.gitsearch.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GitSearchResponse {

    @JsonProperty("total_count")
    public int totalCount;

    @JsonProperty("incomplete_results")
    public boolean incompleteResults;

    @JsonProperty("items")
    public List<GitItem> items;
}
