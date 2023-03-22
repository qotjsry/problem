package com.project.problem.infrastructure;

import com.project.problem.domain.BlogPost;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverBlogPost {
    private String title;
    private String description;
    private String bloggername;

    public BlogPost toBlogPost() {
        BlogPost blogPost = new BlogPost();
        blogPost.setTitle(title);
        blogPost.setContent(description);
        blogPost.setAuthor(bloggername);
        return blogPost;
    }
}
