package com.blog.blogrestapi.dto;

import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
    private long id;
    private String title;
    private String description;
    private String content;
    //this comments variable should match with the Post.class variable since model mapper maps all these variables
    private Set<CommentDto> comments;
}
