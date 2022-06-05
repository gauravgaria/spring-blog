package com.blog.blogrestapi.controller;

import com.blog.blogrestapi.dto.PostDto;
import com.blog.blogrestapi.entity.Post;
import com.blog.blogrestapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    //create blog post
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    // get all posts
   /* @GetMapping
    public List<PostDto> getAllPosts(){
        return postService.getAllPosts();
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // update post by rest api
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable(name = "id") long id){
        PostDto postResponse = postService.updatePost(postDto,id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post deleted successfully.", HttpStatus.OK);
    }

    @GetMapping
    public List<PostDto> getAllPosts(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                     @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize){


        return postService.getAllPosts(pageNo,pageSize);
    }
}
