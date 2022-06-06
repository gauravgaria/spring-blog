package com.blog.blogrestapi.dto;

import com.blog.blogrestapi.entity.Post;
import lombok.Data;

@Data
public class CommentDto {
    private long id;
    private String name;
    private String email;
    private String body;
}
