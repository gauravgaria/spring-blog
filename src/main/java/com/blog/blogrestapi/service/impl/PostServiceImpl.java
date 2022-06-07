package com.blog.blogrestapi.service.impl;

import com.blog.blogrestapi.dto.PostDto;
import com.blog.blogrestapi.dto.PostResponse;
import com.blog.blogrestapi.entity.Post;
import com.blog.blogrestapi.exception.ResourceNotFoundException;
import com.blog.blogrestapi.repository.PostRepository;
import com.blog.blogrestapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = mapToEntity(postDto);

        Post responsePost = postRepository.save(post);

        PostDto postDtoResponse = mapToDto(responsePost);

        return postDtoResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        //create sort object
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        // create pageable instance
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        // get content of page
        List<Post> postsList = posts.getContent();

        List<PostDto> postContent = postsList.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postContent);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        // get post by ID
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        //set update values in post obj
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        // save post
        Post updatedPost = postRepository.save(post);

        return mapToDto(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        postRepository.delete(post);
    }

    private PostDto mapToDto(Post post){
        // conversion using model mapper
        PostDto postDtoResponse = modelMapper.map(post,PostDto.class);  // map(source[converted from], destination[converted into])

        /** Manual conversion
         * */
    /*   PostDto postDtoResponse = new PostDto();
        postDtoResponse.setId(post.getId());
        postDtoResponse.setContent(post.getContent());
        postDtoResponse.setDescription(post.getDescription());
        postDtoResponse.setTitle(post.getTitle());*/

        return  postDtoResponse;
    }

    private Post mapToEntity(PostDto postDto){
        // conversion using model mapper
        Post post  = modelMapper.map(postDto,Post.class);  // map(source[converted from], destination[converted into])

        /** Manual conversion
         * */

       /* Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());*/

        return  post;
    }
}
