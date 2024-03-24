package com.example.demo.src.post.repository;

import com.example.demo.common.entity.BaseEntity.State;
import com.example.demo.src.post.entity.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostFileRepository extends JpaRepository<PostFile, Long> {

    List<PostFile> findByPostIdAndState(Long postId, State state);

    Optional<PostFile> findByIdAndState(Long fileId, State state);

    @Query("SELECT p FROM PostFile p WHERE p.state = 'ACTIVE' AND p.postId IN :postIds")
    List<PostFile> findByPostIds(List<Long> postIds);

    void deleteByState(State state);

    void deleteByPostId(Long postId);

    List<PostFile> findByState(State state);
}
