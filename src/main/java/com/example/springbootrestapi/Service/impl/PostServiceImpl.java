package com.example.springbootrestapi.Service.impl;

import com.example.springbootrestapi.DTO.PostDTO;
import com.example.springbootrestapi.Entity.Category;
import com.example.springbootrestapi.Entity.Post;
import com.example.springbootrestapi.Entity.User;
import com.example.springbootrestapi.Exception.ResourceNotFoundException;
import com.example.springbootrestapi.Repository.CategoryRepository;
import com.example.springbootrestapi.Repository.PostRepository;
import com.example.springbootrestapi.Repository.UserRepository;
import com.example.springbootrestapi.Service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private ModelMapper mapper;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper, UserRepository userRepository,CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.categoryRepository=categoryRepository;
    }


    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Post post=mapper.map(postDTO,Post.class);
        post.setUsername(username);
        Category category = categoryRepository.findById(postDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDTO.getCategoryId()));
        post.setCategory(category);
        Post newpost = postRepository.save(post);
        PostDTO postRespone=mapper.map(post,PostDTO.class);
        return postRespone;
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> posts=postRepository.findAll();
        return posts.stream().map(post ->mapper.map(post,PostDTO.class)).collect(Collectors.toList());
    }

    @Override
    public PostDTO getPostById(long id) {
        Post post=postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        PostDTO postDTO=mapper.map(post,PostDTO.class);
        return postDTO;
    }

    @Override
    public String updatePostByAuthor(PostDTO postDTO, long id) {
        Post post=postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        if(getCurrentUsername().equals(post.getUsername())){
            post.setTitle(postDTO.getTitle());
            post.setContent(postDTO.getContent());
            post.setDescription(postDTO.getDescription());
            Category category = categoryRepository.findById(postDTO.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDTO.getCategoryId()));
            post.setCategory(category);
            postRepository.save(post);
            return "Post updated Successfully";
        }
        else return "You cannot update this post";

    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, long id) {
        Post post=postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle("This Post Updated By Admin"+postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setDescription(postDTO.getDescription());
        Category category = categoryRepository.findById(postDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDTO.getCategoryId()));
        post.setCategory(category);
        Post update=postRepository.save(post);
        PostDTO response=mapper.map(update,PostDTO.class);

        return response;
    }

    @Override
    public void deletePost(long id) {
        Post post=postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    public String getCurrentUsername(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return username;
    }
}
