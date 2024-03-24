package com.example.demo.src.user.repository;

import com.example.demo.src.admin.model.request.AdminUserSearchReq;
import com.example.demo.src.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    @Override
    public Page<User> findAllBy(AdminUserSearchReq adminUserSearchReq, Pageable page) {
        return null;
    }
}
