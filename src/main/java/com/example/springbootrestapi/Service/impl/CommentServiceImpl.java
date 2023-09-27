package com.example.springbootrestapi.Service.impl;

import com.example.springbootrestapi.DTO.CommentDTO;
import com.example.springbootrestapi.Entity.Comment;
import com.example.springbootrestapi.Entity.Post;
import com.example.springbootrestapi.Entity.User;
import com.example.springbootrestapi.Entity.Views;
import com.example.springbootrestapi.Exception.ApiException;
import com.example.springbootrestapi.Exception.ResourceNotFoundException;
import com.example.springbootrestapi.Repository.CommentRepository;
import com.example.springbootrestapi.Repository.PostRepository;
import com.example.springbootrestapi.Repository.UserRepository;
import com.example.springbootrestapi.Repository.ViewRepository;
import com.example.springbootrestapi.Service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;
    private ViewRepository viewRepository;
    private ModelMapper mapper;
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,
                              ModelMapper mapper, UserRepository userRepository,
                              ViewRepository viewRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.userRepository=userRepository;
        this.viewRepository=viewRepository;
    }
    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));


        if(viewRepository.existsByPostIdAndUserId(post.getId(), user.getId())){
            Comment comment=mapper.map(commentDto,Comment.class);
            comment.setPost(post);
            comment.setUser(user);
            Comment newcomment=commentRepository.save(comment);
            return mapper.map(newcomment,CommentDTO.class);
        }
        else {

            throw new ApiException(HttpStatus.BAD_REQUEST, "If you want to send comment for this post, you must read firstly");



        }
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapper.map(comment,CommentDTO.class)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new ApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        return mapper.map(comment,CommentDTO.class);
    }

    @Override
    public CommentDTO updateComment(Long commentId, CommentDTO request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        if(comment.getUser().equals(user)){
            comment.setName(request.getName());
            comment.setBody(request.getBody());
            comment.setEmail(request.getEmail());
            Comment updatedComment = commentRepository.save(comment);
            return mapper.map(updatedComment,CommentDTO.class);
        }
        else {
            throw new ApiException(HttpStatus.BAD_REQUEST, "If you are not the person who author this comment, you cannot update it.");
        }
    }
    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new ApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");


        }
        commentRepository.delete(comment);
    }
}
