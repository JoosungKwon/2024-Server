package com.example.demo.src.comment.repository;

import com.example.demo.src.comment.entity.view.CommentListView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentListViewRepository extends JpaRepository<CommentListView, Long> {

    Page<CommentListView> findAllByPostId(Long id, Pageable pageable);

    List<CommentListView> findAllByPostId(Long id);
}
