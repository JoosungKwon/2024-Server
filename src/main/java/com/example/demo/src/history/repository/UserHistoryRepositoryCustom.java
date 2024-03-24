package com.example.demo.src.history.repository;

import com.example.demo.src.admin.model.request.AdminHistorySearchReq;
import com.example.demo.src.history.entity.UserHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserHistoryRepositoryCustom {

    Page<UserHistory> findAllBy(AdminHistorySearchReq adminHistorySearchReq, Pageable page);
}
