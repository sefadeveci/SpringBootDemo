package com.example.springbootrestapi.Service;

import com.example.springbootrestapi.DTO.ViewDTO;
import com.example.springbootrestapi.Entity.Views;

public interface ViewService {
    void viewPost(long id);
    int viewedCountByPostID(int id);
}
