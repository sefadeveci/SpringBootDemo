package com.example.springbootrestapi.Controller;

import com.example.springbootrestapi.DTO.PostDTO;
import com.example.springbootrestapi.Service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    @PostMapping
   // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
    @GetMapping
    public List<PostDTO> getPosts(){
        return postService.getAllPosts();
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable(name = "id")long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }
    @PutMapping("/byAuthor/{id}")
    public String updatePostByAuthor(@Valid @RequestBody PostDTO postDto,@PathVariable(name = "id")long id){
       return postService.updatePostByAuthor(postDto,id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDto,@PathVariable(name = "id")long id){
        return ResponseEntity.ok(postService.updatePost(postDto,id));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id")long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Post Deleted",HttpStatus.OK);
    }
}
