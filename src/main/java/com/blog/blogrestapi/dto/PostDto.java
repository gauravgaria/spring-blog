package com.blog.blogrestapi.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class PostDto {
    private long id;
    @NotEmpty
    @Size(min = 2, message = "Post title should be at least 2 characters")
    private String title;
    @NotEmpty
    @Size(min = 10, message = "Post description should be at least 10 characters")
    private String description;
    @NotEmpty
    private String content;
    //this comments variable should match with the Post.class variable since model mapper maps all these variables
    private Set<CommentDto> comments;
}
