package com.example.demo.common.scheduler;

import com.example.demo.common.cloud.storage.service.S3Service;
import com.example.demo.common.entity.BaseEntity.State;
import com.example.demo.src.post.repository.PostFileRepository;
import com.example.demo.src.post.repository.PostLikeRepository;
import com.example.demo.src.post.repository.PostRepository;
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
public class PostScheduleService {

    private final S3Service s3Service;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostFileRepository postFileRepository;


    @Async
    @Scheduled(cron = "0 0 * * * *", zone = "Asia/Seoul")
    public void deletePost() {
        log.info("Start to delete inactive post");
        postRepository.deleteByState(State.INACTIVE);
        log.info("End to delete inactive post");
    }


    @Async
    @Scheduled(cron = "0 0 * * * *", zone = "Asia/Seoul")
    public void deletePostFile() {
        log.info("Start to delete inactive post file");
        postFileRepository.findByState(State.INACTIVE)
                .forEach(postFile -> {
                    // S3 파일 삭제
                    s3Service.deleteS3File(postFile.getRegion(), postFile.getBucketName(), postFile.getFilePath());
                });

        postFileRepository.deleteByState(State.INACTIVE);

        log.info("End to delete inactive post file");
    }


    @Async
    @Scheduled(cron = "0 0 * * * *", zone = "Asia/Seoul")
    public void deletePostLike() {
        log.info("Start to delete inactive post like");
        postLikeRepository.deleteByState(State.INACTIVE);
        log.info("End to delete inactive post like");
    }

}
