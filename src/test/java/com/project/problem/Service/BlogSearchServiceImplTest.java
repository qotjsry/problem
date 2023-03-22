package com.project.problem.Service;

import com.project.problem.domain.BlogPost;
import com.project.problem.domain.SearchHistory;
import com.project.problem.infrastructure.KakaoApiAdapter;
import com.project.problem.infrastructure.NaverApiAdapter;
import com.project.problem.repository.SearchHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class BlogSearchServiceImplTest {
    @Mock
    private KakaoApiAdapter kakaoApiAdapter;

    @Mock
    private NaverApiAdapter naverApiAdapter;

    @Mock
    private SearchHistoryRepository searchHistoryRepository;

    @InjectMocks
    private BlogSearchServiceImpl blogSearchService;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void setUp() {
        List<BlogPost> blogPosts = Arrays.asList(
                new BlogPost("Title 1", "Content 1", "Author 1"),
                new BlogPost("Title 2", "Content 2", "Author 2")
        );

        Page<BlogPost> blogPostPage = new PageImpl<>(blogPosts, PageRequest.of(0, 20), blogPosts.size());

        when(kakaoApiAdapter.searchBlogPosts("test", "accuracy", 1, 20))
                .thenReturn(blogPostPage);

        when(searchHistoryRepository.findBySearchTerm("test"))
                .thenReturn(Optional.of(new SearchHistory("test", 1)));
    }

    @Test
    public void testSearchBlogPosts() {
        Page<BlogPost> blogPostPage = blogSearchService.searchBlogPosts("test", "accuracy", 1, 10);
        assertEquals(2, blogPostPage.getContent().size());
        verify(blogSearchService, times(1)).searchBlogPosts("test", "accuracy", 1, 10);
        verify(searchHistoryRepository, times(1)).findBySearchTerm("test");
        verify(searchHistoryRepository, times(1)).save(any(SearchHistory.class));

        System.out.println("dd");
    }


    @Test
    void getPopularSearchTerms() {
        SearchHistory test1 = new SearchHistory("test1", 5);
        SearchHistory test2 = new SearchHistory("test2", 3);
        SearchHistory test3 = new SearchHistory("test3", 7);

        // Set up the mock repository
        when(searchHistoryRepository.findAll(any(PageRequest.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(test3, test1)));

        // Call the service method
        List<SearchHistory> popularSearchTerms = blogSearchService.getPopularSearchTerms(2);

        // Verify the results
        assertThat(popularSearchTerms).hasSize(2);
        assertThat(popularSearchTerms.get(0).getSearchTerm()).isEqualTo("test3");
        assertThat(popularSearchTerms.get(0).getSearchCount()).isEqualTo(7);
        assertThat(popularSearchTerms.get(1).getSearchTerm()).isEqualTo("test1");
        assertThat(popularSearchTerms.get(1).getSearchCount()).isEqualTo(5);

        verify(searchHistoryRepository, times(1)).findAll(any(PageRequest.class));
    }
}