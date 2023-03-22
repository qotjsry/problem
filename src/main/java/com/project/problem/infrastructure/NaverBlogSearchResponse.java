package com.project.problem.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.problem.domain.BlogPost;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class NaverBlogSearchResponse {
    @JsonProperty("items")
    private List<NaverBlogPost> naverBlogPosts;

    @JsonProperty("total")
    private int totalCount;

    public List<BlogPost> toBlogPosts() {
        return naverBlogPosts.stream()
                .map(NaverBlogPost::toBlogPost)
                .collect(Collectors.toList());
    }
}
