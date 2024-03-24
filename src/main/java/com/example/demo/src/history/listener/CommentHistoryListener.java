package com.example.demo.src.history.listener;

import com.example.demo.src.history.entity.CommentHistory;
import com.example.demo.src.history.event.CommentHistoryEvent;
import com.example.demo.src.history.repository.CommentHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
@RequiredArgsConstructor
public class CommentHistoryListener {

    private final CommentHistoryRepository commentHistoryRepository;


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void createCommentHistory(CommentHistoryEvent event) {
        log.debug("createCommentHistory event: {}", event);

        CommentHistory commentHistory = CommentHistory.builder()
                .operationType(event.getOperationType())
                .commentId(event.getCommentId())
                .postId(event.getPostId())
                .userId(event.getUserId())
                .build();

        commentHistoryRepository.save(commentHistory);
    }

}
