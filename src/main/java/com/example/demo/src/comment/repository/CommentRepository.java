package com.example.demo.src.comment.repository;

import com.example.demo.src.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.demo.common.entity.BaseEntity.State;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndState(Long commentId, State state);

    List<Comment> findByPostIdAndState(Long postId, State state);

    int countByPostIdAndState(Long postId, State state);

    Page<Comment> findAllBy(Long postId, Pageable pageable);

    void deleteByPostId(Long postId);

    List<Comment> findAllByPostIdAndState(Long postId, State state);

    Page<Comment> findAllByPostIdAndState(Long postId, State state, Pageable pageable);

    void deleteByState(State state);
}
