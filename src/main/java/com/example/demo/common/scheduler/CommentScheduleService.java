package com.example.demo.common.scheduler;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class CommentScheduleService {

    private final CommentRepository commentRepository;


    @Async
    @Scheduled(cron = "0 0 * * * *", zone = "Asia/Seoul")
    public void deleteComment() {
        log.info("Start to delete inactive comment");
        commentRepository.deleteByState(BaseEntity.State.INACTIVE);
        log.info("End to delete inactive comment");
    }


}
