package com.example.demo.src.comment.service;

import com.example.demo.common.cloud.storage.service.S3Service;
import com.example.demo.common.exceptions.NotFoundException;
import com.example.demo.common.exceptions.ServicePolicyException;
import com.example.demo.common.model.OperationType;
import com.example.demo.common.model.response.BaseResponseStatus;
import com.example.demo.common.prop.ApplicationProp;
import com.example.demo.common.prop.model.UserFileBucket;
import com.example.demo.src.comment.entity.Comment;
import com.example.demo.src.comment.entity.view.CommentListView;
import com.example.demo.src.comment.model.request.CommentCreateReq;
import com.example.demo.src.comment.model.request.CommentDeleteReq;
import com.example.demo.src.comment.model.request.CommentUpdateReq;
import com.example.demo.src.comment.model.response.CommentCreateRes;
import com.example.demo.src.comment.model.response.CommentDetailRes;
import com.example.demo.src.comment.model.response.CommentUpdateRes;
import com.example.demo.src.comment.repository.CommentListViewRepository;
import com.example.demo.src.comment.repository.CommentRepository;
import com.example.demo.src.history.event.CommentHistoryEvent;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.post.repository.PostRepository;
import com.example.demo.src.user.entity.User;
import com.example.demo.src.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.demo.common.Constant.FilePath.PROFILE_IMAGE;
import static com.example.demo.common.entity.BaseEntity.State.ACTIVE;
import static com.example.demo.common.model.response.BaseResponseStatus.*;

@Service
@Validated
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final ApplicationProp applicationProp;
    private final S3Service s3Service;
    private final CommentRepository commentRepository;
    private final CommentListViewRepository commentListViewRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    @Transactional
    public CommentCreateRes createCommentBy(CommentCreateReq request) {
        Post post = postRepository.findByIdAndState(request.getPostId(), ACTIVE)
                .orElseThrow(() -> new NotFoundException(POST_NOT_FOUND));

        User user = userRepository.findByIdAndState(request.getUserId(), ACTIVE)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        Comment comment = Comment.builder()
                .content(request.getContent())
                .postId(post.getId())
                .userId(request.getUserId())
                .build();

        Comment saveComment = commentRepository.save(comment);

        applicationEventPublisher.publishEvent(CommentHistoryEvent.from(OperationType.CREATE, saveComment, user));

        return CommentCreateRes.from(saveComment);
    }


    @Transactional
    public CommentUpdateRes updateCommentBy(CommentUpdateReq request) {
        Comment comment = commentRepository.findByIdAndState(request.getCommentId(), ACTIVE)
                .orElseThrow(() -> new NotFoundException(COMMENT_NOT_FOUND));

        verifySameMember(comment.getUserId(), request.getUserId());

        User user = userRepository.findByIdAndState(request.getUserId(), ACTIVE)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        comment.updateContent(request.getContent());

        applicationEventPublisher.publishEvent(CommentHistoryEvent.from(OperationType.UPDATE, comment, user));

        return CommentUpdateRes.from(comment);
    }


    @Transactional
    public void deleteCommentBy(CommentDeleteReq request) {
        Comment comment = commentRepository.findByIdAndState(request.getCommentId(), ACTIVE)
                .orElseThrow(() -> new NotFoundException(COMMENT_NOT_FOUND));

        verifySameMember(comment.getUserId(), request.getUserId());

        User user = userRepository.findByIdAndState(request.getUserId(), ACTIVE)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        comment.inActive(); // Soft Delete

        applicationEventPublisher.publishEvent(CommentHistoryEvent.from(OperationType.DELETE, comment, user));
    }


    public Page<CommentDetailRes> getCommentsBy(Long postId, Pageable pageable) {
        Post post = postRepository.findByIdAndState(postId, ACTIVE)
                .orElseThrow(() -> new NotFoundException(POST_NOT_FOUND));

        Page<CommentDetailRes> comments = commentListViewRepository.findAllByPostId(post.getId(), pageable)
                .map(this::getCommentDetailResWithProfileImageUrl);

        return comments;
    }


    public List<CommentDetailRes> getCommentsBy(Long postId) {
        Post post = postRepository.findByIdAndState(postId, ACTIVE)
                .orElseThrow(() -> new NotFoundException(POST_NOT_FOUND));

        List<CommentDetailRes> comments = commentListViewRepository.findAllByPostId(post.getId())
                .stream()
                .map(this::getCommentDetailResWithProfileImageUrl)
                .collect(Collectors.toList());

        return comments;
    }


    private void verifySameMember(Long writerId, Long userId) {
        if (!Objects.equals(writerId, userId)) {
            throw new ServicePolicyException(BaseResponseStatus.NOT_MATCH_USER);
        }
    }


    private CommentDetailRes getCommentDetailResWithProfileImageUrl(CommentListView commentListView) {
        UserFileBucket userFileBucket = applicationProp.getStorage().getUserFileBucket();
        Long downloadUrlExpirationSeconds = userFileBucket.getDownloadUrlExpirationSeconds();

        URL preSignedUrl = s3Service.getDownloadUrl(userFileBucket.getRegion(), userFileBucket.getBucketName(),
                commentListView.getUserFilePath() + PROFILE_IMAGE, downloadUrlExpirationSeconds);

        return CommentDetailRes.from(commentListView, preSignedUrl.toString());
    }

}


