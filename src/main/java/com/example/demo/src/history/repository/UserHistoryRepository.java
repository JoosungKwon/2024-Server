package com.example.demo.src.history.repository;

import com.example.demo.src.history.entity.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHistoryRepository extends UserHistoryRepositoryCustom, JpaRepository<UserHistory, Long> {

}
