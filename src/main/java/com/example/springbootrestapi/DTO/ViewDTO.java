package com.example.springbootrestapi.DTO;

import com.example.springbootrestapi.Entity.Post;
import com.example.springbootrestapi.Entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViewDTO {
    private long id;
    private User user;
    private Post post;

}
