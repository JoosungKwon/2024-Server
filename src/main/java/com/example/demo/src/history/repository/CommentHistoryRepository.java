package com.example.demo.src.history.repository;

import com.example.demo.src.history.entity.CommentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentHistoryRepository extends CommentHistoryRepositoryCustom, JpaRepository<CommentHistory, Long> {

}
