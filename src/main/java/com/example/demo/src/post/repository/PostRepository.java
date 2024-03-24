package com.example.demo.src.post.repository;

import com.example.demo.src.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static com.example.demo.common.entity.BaseEntity.State;

public interface PostRepository extends PostRepositoryCustom, JpaRepository<Post, Long> {

    Optional<Post> findByIdAndState(Long postId, State state);

    void deleteByState(State state);
}
