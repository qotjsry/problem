package com.project.problem.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.problem.domain.BlogPost;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class KakaoBlogSearchResponse {

    @JsonProperty("documents")
    private List<KakaoBlogPost> kakaoBlogPosts;

    @JsonProperty("meta")
    private KakaoBlogSearchMeta meta;

    public List<BlogPost> toBlogPosts() {
        return kakaoBlogPosts.stream()
                .map(KakaoBlogPost::toBlogPost)
                .collect(Collectors.toList());
    }


    public int getTotalCount() {
        return meta.getTotalCount();
    }
}
