package com.project.problem.infrastructure;

import com.project.problem.domain.BlogPost;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class NaverApiAdapter {
    @Value("${naver.api.id}")
    private String naverApiClientId;

    @Value("${naver.api.secret}")
    private String naverApiClientSecret;

    public Page<BlogPost> searchBlogPosts(String query, String sort, int page, int size) {
        System.out.println("11");
        if(sort.equals("accuracy")){
            sort = "sim";
        }else{
            sort = "date";
        }
        String url = "https://openapi.naver.com/v1/search/blog?query=" + query + "&sort=" + sort + "&start=" + ((page - 1) * size + 1) + "&display=" + size;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("X-Naver-Client-Id", naverApiClientId);
            request.getHeaders().add("X-Naver-Client-Secret", naverApiClientSecret);
            return execution.execute(request, body);
        });

        ResponseEntity<NaverBlogSearchResponse> response = restTemplate.getForEntity(url, NaverBlogSearchResponse.class);
        int totalCount = response.getBody().getTotalCount();
        System.out.println(totalCount);
        Pageable pageable = PageRequest.of(page - 1, size);
        return new PageImpl<>(response.getBody().toBlogPosts(), pageable, totalCount);
    }
}