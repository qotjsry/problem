package com.project.problem.api;

import com.project.problem.Service.BlogSearchService;
import com.project.problem.domain.BlogPost;
import com.project.problem.domain.SearchHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BlogController {
    private final BlogSearchService blogSearchService;

    @Autowired
    public BlogController(BlogSearchService blogSearchService) {
        this.blogSearchService = blogSearchService;
    }



    @GetMapping("/api/search")
    public ResponseEntity<Page<BlogPost>> searchBlogPosts(
            @RequestParam String query,
            @RequestParam(required = false, defaultValue = "accuracy") String sort,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {

        try {
            Page<BlogPost> blogPosts = blogSearchService.searchBlogPosts(query, sort, page, size);
            return new ResponseEntity<>(blogPosts, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/popular-search-terms")
    public List<SearchHistory> getPopularSearchTerms(@RequestParam(required = false, defaultValue = "10") int limit) {
        return blogSearchService.getPopularSearchTerms(limit);
    }

}

