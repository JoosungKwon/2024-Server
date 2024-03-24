package com.example.demo.src.admin.service;

import com.example.demo.src.admin.model.request.AdminUserSearchReq;
import com.example.demo.src.admin.model.response.AdminUserRes;
import com.example.demo.src.user.entity.User;
import com.example.demo.src.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminUserManageService {

    private final UserRepository userRepository;

    public Page<AdminUserRes> getUsersBy(AdminUserSearchReq request, Pageable pageable) {
        Page<User> users = userRepository.findAllBy(request, pageable);

        return users.map(AdminUserRes::from);
    }
}
