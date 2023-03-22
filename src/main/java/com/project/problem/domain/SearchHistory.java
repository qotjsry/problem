package com.project.problem.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Entity
public class SearchHistory {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String searchTerm;
    private Integer searchCount;

    public SearchHistory(String searchTerm, int searchCount) {
        this.searchTerm = searchTerm;
        this.searchCount = searchCount;
    }

    public SearchHistory(){

    }

    public void incrementSearchCount() {
        this.searchCount++;
    }
}
