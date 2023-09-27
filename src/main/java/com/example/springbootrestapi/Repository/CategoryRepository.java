package com.example.springbootrestapi.Repository;

import com.example.springbootrestapi.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
