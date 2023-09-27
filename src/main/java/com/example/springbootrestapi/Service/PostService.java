package com.example.springbootrestapi.Service;

import com.example.springbootrestapi.DTO.PostDTO;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);
    List<PostDTO> getAllPosts();
    PostDTO getPostById(long id);
    String updatePostByAuthor(PostDTO postDTO,long id);
    PostDTO updatePost(PostDTO postDTO, long id);
    void deletePost(long id);
}
