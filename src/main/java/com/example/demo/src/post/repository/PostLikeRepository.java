package com.example.demo.src.post.repository;

import com.example.demo.src.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import static com.example.demo.common.entity.BaseEntity.State;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Optional<PostLike> findByPostIdAndUserId(Long postId, Long userId);

    List<PostLike> findByPostIdAndState(Long postId, State state);

    void deleteByState(State state);

    void deleteByPostId(Long postId);
}
