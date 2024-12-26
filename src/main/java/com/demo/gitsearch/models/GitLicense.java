package com.demo.gitsearch.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GitLicense {

    @JsonProperty("key")
    public String key;

    @JsonProperty("name")
    public String name;

    @JsonProperty("url")
    public String url;

    @JsonProperty("spdx_id")
    public String spdxId;

    @JsonProperty("node_id")
    public String nodeId;

    @JsonProperty("html_url")
    public String htmlUrl;
}
