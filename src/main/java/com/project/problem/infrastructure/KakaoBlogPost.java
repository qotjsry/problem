package com.project.problem.infrastructure;


import com.project.problem.domain.BlogPost;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoBlogPost {
    private String title;
    private String contents;
    private String blogname;

    public BlogPost toBlogPost() {
        BlogPost blogPost = new BlogPost();
        blogPost.setTitle(title);
        blogPost.setContent(contents);
        blogPost.setAuthor(blogname);
        return blogPost;
    }
}
