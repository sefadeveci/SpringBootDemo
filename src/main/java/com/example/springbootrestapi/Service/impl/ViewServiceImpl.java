package com.example.springbootrestapi.Service.impl;

import com.example.springbootrestapi.DTO.ViewDTO;
import com.example.springbootrestapi.Entity.Post;
import com.example.springbootrestapi.Entity.Views;
import com.example.springbootrestapi.Exception.ResourceNotFoundException;
import com.example.springbootrestapi.Repository.PostRepository;
import com.example.springbootrestapi.Repository.UserRepository;
import com.example.springbootrestapi.Repository.ViewRepository;
import com.example.springbootrestapi.Service.ViewService;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ViewServiceImpl implements ViewService {
    private ViewRepository viewRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;
    private ModelMapper mapper;
    public ViewServiceImpl(ViewRepository viewRepository, PostRepository postRepository,ModelMapper mapper, UserRepository userRepository) {
        this.viewRepository = viewRepository;
        this.postRepository = postRepository;
        this.mapper=mapper;
        this.userRepository= userRepository;
    }


    @Override
    public void viewPost(long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Post post=postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("View", "id", id));
        ViewDTO newDto = new ViewDTO();
        newDto.setUser(userRepository.findByEmail(username));
        newDto.setPost(post);
        Views views=mapper.map(newDto,Views.class);
        viewRepository.save(views);

    }

    @Override
    public int viewedCountByPostID(int id) {
        Collection<Views> viewsList=viewRepository.viewedCountByPostID(id);
        return viewsList.size();
    }
}
