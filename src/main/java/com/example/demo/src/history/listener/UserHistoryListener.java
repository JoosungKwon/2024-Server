package com.example.demo.src.history.listener;

import com.example.demo.src.history.entity.UserHistory;
import com.example.demo.src.history.event.UserHistoryEvent;
import com.example.demo.src.history.repository.UserHistoryRepository;
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
public class UserHistoryListener {

    private final UserHistoryRepository userHistoryRepository;


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void createPostHistory(UserHistoryEvent event) {
        log.debug("createUserHistory event: {}", event);

        UserHistory userHistory = UserHistory.builder()
                .operationType(event.getOperationType())
                .userId(event.getUserId())
                .build();

        userHistoryRepository.save(userHistory);
    }

}
