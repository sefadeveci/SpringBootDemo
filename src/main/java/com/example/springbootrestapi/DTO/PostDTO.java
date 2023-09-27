package com.example.springbootrestapi.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
public class PostDTO {
    private long id;
    @NotEmpty
    @Size(min = 2,message = "Post title should have at least 2 characters")
    private String title;
    @NotEmpty
    @Size(min = 10,message = "Post description should have at least 10 characters")
    private String description;
    @NotEmpty(message = "DeğerDeğerDeğer")
    private String content;

    private String username;
    private Long categoryId;
}
