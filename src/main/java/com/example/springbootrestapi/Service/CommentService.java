package com.example.springbootrestapi.Service;

import com.example.springbootrestapi.DTO.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(long postId, CommentDTO commentDto);
    List<CommentDTO> getCommentsByPostId(long postId);
    CommentDTO getCommentById(Long postId, Long commentId);
    CommentDTO updateComment(Long commentId,CommentDTO commentDTO);
    void deleteComment(Long postId, Long commentId);
}
