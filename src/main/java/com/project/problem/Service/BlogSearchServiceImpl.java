package com.project.problem.Service;


import com.project.problem.domain.BlogPost;
import com.project.problem.domain.SearchHistory;
import com.project.problem.infrastructure.KakaoApiAdapter;
import com.project.problem.infrastructure.NaverApiAdapter;
import com.project.problem.repository.SearchHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogSearchServiceImpl implements BlogSearchService {

    private KakaoApiAdapter kakaoApiAdapter;
    private SearchHistoryRepository searchHistoryRepository;
    private NaverApiAdapter naverApiAdapter;

    @Autowired
    public BlogSearchServiceImpl(KakaoApiAdapter kakoApiAdapter, SearchHistoryRepository searchHistoryRepository,NaverApiAdapter naverApiAdapter) {
        this.kakaoApiAdapter = kakoApiAdapter;
        this.searchHistoryRepository = searchHistoryRepository;
        this.naverApiAdapter = naverApiAdapter;
    }

    @Override
    public Page<BlogPost> searchBlogPosts(String query, String sort, int page, int size) {
        Page<BlogPost> blogPostPage;
        try {
            blogPostPage = kakaoApiAdapter.searchBlogPosts(query, sort, page, size);
        } catch (Exception e) {
            blogPostPage = naverApiAdapter.searchBlogPosts(query, sort, page, size);
        }
        SearchHistory searchHistory = searchHistoryRepository.findBySearchTerm(query)
                .orElse(new SearchHistory(query, 0));
        searchHistory.incrementSearchCount();
        searchHistoryRepository.save(searchHistory);

        return blogPostPage;
    }

    @Override
    public List<SearchHistory> getPopularSearchTerms(int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "searchCount"));
        return searchHistoryRepository.findAll(pageable).getContent();
    }

}
