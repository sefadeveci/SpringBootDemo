package com.example.springbootrestapi.Repository;

import com.example.springbootrestapi.Entity.Post;
import com.example.springbootrestapi.Entity.Views;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ViewRepository extends JpaRepository<Views,Long> {
@Query(value = "SELECT * FROM restapi.views where post_id=:postID", nativeQuery = true)
Collection<Views> viewedCountByPostID(@Param("postID") int postID);
Boolean existsByPostIdAndUserId(long post_id,long user_id);
}
