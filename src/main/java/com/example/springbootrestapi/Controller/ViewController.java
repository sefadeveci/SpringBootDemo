package com.example.springbootrestapi.Controller;

import com.example.springbootrestapi.DTO.PostDTO;
import com.example.springbootrestapi.DTO.ViewDTO;
import com.example.springbootrestapi.Service.ViewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/views")
public class ViewController {
    public ViewController(ViewService viewService) {
        this.viewService = viewService;
    }

    private ViewService viewService;
    @PutMapping("/{id}")
    public void createView(@Valid @PathVariable(name = "id")long id){
        viewService.viewPost(id);
    }

    @GetMapping("/{id}")
    public int viewedCountByPostID(@PathVariable(name = "id")int id){
        return viewService.viewedCountByPostID(id);
    }
}
