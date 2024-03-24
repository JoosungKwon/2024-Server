package com.example.demo.src.admin.service;

import com.example.demo.common.cloud.storage.service.S3Service;
import com.example.demo.src.admin.model.request.AdminPostSearchReq;
import com.example.demo.src.admin.model.response.AdminPostDetail;
import com.example.demo.src.admin.model.response.AdminPostRes;
import com.example.demo.src.comment.repository.CommentRepository;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.post.repository.PostFileRepository;
import com.example.demo.src.post.repository.PostLikeRepository;
import com.example.demo.src.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.common.entity.BaseEntity.State.ACTIVE;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminFeedService {

    private final S3Service s3Service;
    private final PostRepository postRepository;
    private final PostFileRepository postFileRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
        postLikeRepository.deleteByPostId(postId);
        postFileRepository.findByPostIdAndState(postId, ACTIVE)
                .forEach(postFile -> {
                    s3Service.deleteS3File(postFile.getRegion(), postFile.getBucketName(), postFile.getFilePath());
                    postFileRepository.delete(postFile);
                });
        commentRepository.deleteByPostId(postId);
    }

    public Page<AdminPostRes> getPostsBy(AdminPostSearchReq adminPostSearchReq, Pageable pageable) {
        Page<Post> posts = postRepository.findAllBy(adminPostSearchReq, pageable);

        return posts.map(AdminPostRes::from);
    }

    public AdminPostDetail getPostDetailForAdmin(Long postId) {
        Post post = postRepository.findByIdAndState(postId, ACTIVE)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));

        return AdminPostDetail.from(post);
    }

}
