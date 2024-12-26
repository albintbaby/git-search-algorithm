package com.demo.gitsearch.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GitPopularityResponse {
    private String name;
    private String fullName;
    private String gitUrl;
    private BigDecimal popularity;
    private Integer stars;
    private Integer forks;
    private Date lastUpdated;
}
