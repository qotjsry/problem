package com.project.problem.Service;


import com.project.problem.domain.BlogPost;
import com.project.problem.domain.SearchHistory;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BlogSearchService {

    Page<BlogPost> searchBlogPosts(String query, String sort, int page, int size);

    public List<SearchHistory> getPopularSearchTerms(int limit);
}
