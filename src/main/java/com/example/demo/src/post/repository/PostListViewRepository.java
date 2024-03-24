package com.example.demo.src.post.repository;

import com.example.demo.src.post.entity.view.PostListView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostListViewRepository extends JpaRepository<PostListView, Long> {
}
