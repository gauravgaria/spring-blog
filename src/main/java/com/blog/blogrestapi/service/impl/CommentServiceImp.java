package com.blog.blogrestapi.service.impl;

import com.blog.blogrestapi.dto.CommentDto;
import com.blog.blogrestapi.entity.Comment;
import com.blog.blogrestapi.entity.Post;
import com.blog.blogrestapi.exception.BlogApiException;
import com.blog.blogrestapi.exception.ResourceNotFoundException;
import com.blog.blogrestapi.repository.CommentRepository;
import com.blog.blogrestapi.repository.PostRepository;
import com.blog.blogrestapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImp implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);

        // retrieve post id
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));

        // set post to comment entity
        comment.setPost(post);

        //comment entity to DB
        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long id) {
        // retrieve comments from postId
        List<Comment> comments = commentRepository.findByPostId(id);

        /** Convert list of comment entities to list of comment DTO's
         *  stream -> map -> selects each comment and convert it to DTO and collects that DTO to list.
         *  */
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());

    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        // retrieve post id
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));

        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","id",commentId));

        if(comment.getPost().getId() != post.getId()){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not to the post");
        }

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        // retrieve post id
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));

        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","id",commentId));

        if(comment.getPost().getId() != post.getId()){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not to the post");
        }

        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());

        Comment updatedComment = commentRepository.save(comment);

        return mapToDto(updatedComment);
    }

    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setBody(comment.getBody());
        commentDto.setId(comment.getId());
        commentDto.setEmail(comment.getEmail());
        commentDto.setName(comment.getName());

        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setBody(commentDto.getBody());
        comment.setId(commentDto.getId());
        comment.setEmail(commentDto.getEmail());
        comment.setName(commentDto.getName());

        return comment;
    }
}
