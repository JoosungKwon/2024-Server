package com.example.demo.src.history.listener;

import com.example.demo.src.history.entity.PostHistory;
import com.example.demo.src.history.event.PostHistoryEvent;
import com.example.demo.src.history.repository.PostHistoryRepository;
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
public class PostHistoryListener {

    private final PostHistoryRepository postHistoryRepository;


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void createPostHistory(PostHistoryEvent event) {
        log.debug("createPostHistory event: {}", event);

        PostHistory postHistory = PostHistory.builder()
                .operationType(event.getOperationType())
                .postId(event.getPostId())
                .userId(event.getUserId())
                .userName(event.getUserName())
                .build();

        postHistoryRepository.save(postHistory);
    }

}
