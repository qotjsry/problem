package com.project.problem.infrastructure;

import com.project.problem.domain.BlogPost;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class KakaoApiAdapter {
    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    public Page<BlogPost> searchBlogPosts(String query, String sort, int page, int size) {
        System.out.println("5");
        String url = "https://dapi.kakao.com/v2/search/blog?query=" + query + "&sort=" + sort + "&page=" + page;
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("6");
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "KakaoAK " + kakaoApiKey);
            return execution.execute(request, body);
        });
        System.out.println("3");
        ResponseEntity<KakaoBlogSearchResponse> response = restTemplate.getForEntity(url, KakaoBlogSearchResponse.class);
        int totalCount = response.getBody().getTotalCount();
        Pageable pageable = PageRequest.of(page - 1, size);
        System.out.println("API response: " + response.getBody().toBlogPosts());

        return new PageImpl<>(response.getBody().toBlogPosts(), pageable, totalCount);
    }
}
