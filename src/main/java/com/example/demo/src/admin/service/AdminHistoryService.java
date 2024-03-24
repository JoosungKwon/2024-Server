package com.example.demo.src.admin.service;

import com.example.demo.src.admin.model.request.AdminHistorySearchReq;
import com.example.demo.src.admin.model.response.AdminCommentHistoryRes;
import com.example.demo.src.admin.model.response.AdminPostHistoryRes;
import com.example.demo.src.admin.model.response.AdminUserHistoryRes;
import com.example.demo.src.history.entity.CommentHistory;
import com.example.demo.src.history.entity.PostHistory;
import com.example.demo.src.history.entity.UserHistory;
import com.example.demo.src.history.repository.CommentHistoryRepository;
import com.example.demo.src.history.repository.PostHistoryRepository;
import com.example.demo.src.history.repository.UserHistoryRepository;
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
public class AdminHistoryService {

    private final UserHistoryRepository userHistoryRepository;
    private final PostHistoryRepository postHistoryRepository;
    private final CommentHistoryRepository commentHistoryRepository;


    public Page<AdminUserHistoryRes> getUserHistories(AdminHistorySearchReq adminHistorySearchReq, Pageable pageable) {
        Page<UserHistory> userHistories = userHistoryRepository.findAllBy(adminHistorySearchReq, pageable);
        return userHistories.map(AdminUserHistoryRes::from);
    }


    public Page<AdminCommentHistoryRes> getCommentHistories(AdminHistorySearchReq adminHistorySearchReq, Pageable pageable) {
        Page<PostHistory> postHistories = postHistoryRepository.findAllBy(adminHistorySearchReq, pageable);
        return postHistories.map(AdminCommentHistoryRes::from);
    }


    public Page<AdminPostHistoryRes> getPostHistories(AdminHistorySearchReq adminHistorySearchReq, Pageable pageable) {
        Page<CommentHistory> commentHistories = commentHistoryRepository.findAllBy(adminHistorySearchReq, pageable);
        return commentHistories.map(AdminPostHistoryRes::from);
    }


}
