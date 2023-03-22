package com.project.problem.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoBlogSearchMeta {
    @JsonProperty("total_count")
    private int totalCount;
}