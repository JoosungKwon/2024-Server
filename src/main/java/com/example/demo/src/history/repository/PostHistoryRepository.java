package com.example.demo.src.history.repository;

import com.example.demo.src.history.entity.PostHistory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostHistoryRepository extends PostHistoryRepositoryCustom, JpaRepository<PostHistory, Long> {

}
