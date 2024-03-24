package com.example.demo.src.post.service;

import com.example.demo.common.cloud.storage.service.S3Service;
import com.example.demo.common.entity.BaseEntity;
import com.example.demo.common.exceptions.NotFoundException;
import com.example.demo.common.exceptions.ServicePolicyException;
import com.example.demo.common.model.OperationType;
import com.example.demo.common.prop.ApplicationProp;
import com.example.demo.common.prop.model.PostFileBucket;
import com.example.demo.src.comment.entity.Comment;
import com.example.demo.src.comment.repository.CommentRepository;
import com.example.demo.src.history.event.PostHistoryEvent;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.post.entity.PostFile;
import com.example.demo.src.post.entity.PostLike;
import com.example.demo.src.post.entity.view.PostListView;
import com.example.demo.src.post.model.request.*;
import com.example.demo.src.post.model.response.FileRes;
import com.example.demo.src.post.model.response.PostDetailRes;
import com.example.demo.src.post.model.response.PostLikeRes;
import com.example.demo.src.post.model.response.PostSimpleRes;
import com.example.demo.src.post.repository.PostFileRepository;
import com.example.demo.src.post.repository.PostLikeRepository;
import com.example.demo.src.post.repository.PostListViewRepository;
import com.example.demo.src.post.repository.PostRepository;
import com.example.demo.src.report.repository.ReportRepository;
import com.example.demo.src.user.UserRes;
import com.example.demo.src.user.entity.User;
import com.example.demo.src.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.demo.common.Constant.Char.SLASH;
import static com.example.demo.common.Constant.File.MAX_UPLOAD_FILE_COUNT;
import static com.example.demo.common.entity.BaseEntity.State.ACTIVE;
import static com.example.demo.common.entity.BaseEntity.State.INACTIVE;
import static com.example.demo.common.model.response.BaseResponseStatus.*;
import static com.example.demo.src.post.entity.UploadStatus.FAIL;
import static com.example.demo.src.post.entity.UploadStatus.SUCCESS;
import static com.example.demo.src.report.entity.DomainType.COMMENT;
import static com.example.demo.src.report.entity.DomainType.POST;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final ApplicationProp applicationProp;
    private final S3Service s3Service;
    private final PostRepository postRepository;
    private final PostFileRepository postfileRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostListViewRepository postListViewRepository;
    private final CommentRepository commentRepository;
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;


    @Transactional
    public PostSimpleRes createPostBy(PostCreateReq request) {
        User user = userRepository.findByIdAndState(request.getUserId(), ACTIVE)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .userId(request.getUserId())
                .build();

        Post savedPost = postRepository.save(post);

        PostFileBucket bucket = applicationProp.getStorage().getPostFileBucket();

        // 게시글 포함되는 사진 저장 - 10개 한정
        if (request.getUploadFiles().size() > MAX_UPLOAD_FILE_COUNT) {
            throw new ServicePolicyException(MAX_UPLOAD_FILE_COUNT_EXCEEDED);
        }

        List<FileRes> fileResList = request.getUploadFiles()
                .stream()
                .map(file -> {
                    String fileUUID = UUID.randomUUID().toString();
                    String filePath = StringUtils.joinWith(SLASH, savedPost.getId(), fileUUID);
                    PostFile postFile = PostFile.builder()
                            .postId(post.getId())
                            .uuid(fileUUID)
                            .name(file.getName())
                            .filePath(filePath)
                            .size(file.getSize())
                            .type(file.getType())
                            .region(bucket.getRegion())
                            .bucketName(bucket.getBucketName())
                            .build();

                    postfileRepository.save(postFile);

                    URL preSignedUrl = s3Service.getUploadUrl(bucket.getRegion(), bucket.getBucketName(),
                            postFile.getFilePath() + file.getName(), bucket.getUploadUrlExpirationSeconds());

                    return FileRes.from(postFile, preSignedUrl.toString());
                })
                .collect(Collectors.toList());

        applicationEventPublisher.publishEvent(PostHistoryEvent.from(OperationType.CREATE, savedPost, user));

        return PostSimpleRes.from(savedPost, fileResList);
    }


    @Transactional
    public PostSimpleRes updatePostBy(PostUpdateReq request) {
        User user = userRepository.findByIdAndState(request.getUserId(), ACTIVE)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        Post post = postRepository.findByIdAndState(request.getPostId(), ACTIVE)
                .orElseThrow(() -> new NotFoundException(POST_NOT_FOUND));

        post.changeTitle(request.getTitle());
        post.changeContent(request.getContent());

        List<PostFile> originFiles = postfileRepository.findByPostIdAndState(request.getPostId(), ACTIVE);

        // 10개가 유지되어야 함
        int originFileCount = originFiles.size();
        int newFileCount = request.getNewUploadFiles().size();
        int deleteFileCount = request.getDeleteFiles().size();
        int totalFileCount = originFileCount + newFileCount - deleteFileCount;

        if (totalFileCount > MAX_UPLOAD_FILE_COUNT) {
            throw new ServicePolicyException(MAX_UPLOAD_FILE_COUNT_EXCEEDED);
        }


        List<Long> deleteFileIds = request.getDeleteFiles()
                .stream()
                .map(FileDeleteReq::getFileId)
                .collect(Collectors.toList());

        List<PostFile> deleteFiles = postfileRepository.findAllById(deleteFileIds);

        deleteFiles.forEach(postFile -> {
            postFile.inActive();
            s3Service.deleteS3File(postFile.getRegion(), postFile.getBucketName(), postFile.getFilePath());
        });

        PostFileBucket bucket = applicationProp.getStorage().getPostFileBucket();
        List<FileRes> fileResList = request.getNewUploadFiles()
                .stream()
                .map(file -> {
                    String fileUUID = UUID.randomUUID().toString();
                    String filePath = StringUtils.joinWith(SLASH, post.getId(), fileUUID);
                    PostFile postFile = PostFile.builder()
                            .postId(post.getId())
                            .uuid(fileUUID)
                            .name(file.getName())
                            .filePath(filePath)
                            .size(file.getSize())
                            .type(file.getType())
                            .region(bucket.getRegion())
                            .bucketName(bucket.getBucketName())
                            .build();

                    postfileRepository.save(postFile);

                    URL preSignedUrl = s3Service.getUploadUrl(bucket.getRegion(), bucket.getBucketName(),
                            postFile.getFilePath(), bucket.getUploadUrlExpirationSeconds());

                    return FileRes.from(postFile, preSignedUrl.toString());
                })
                .collect(Collectors.toList());

        applicationEventPublisher.publishEvent(PostHistoryEvent.from(OperationType.UPDATE, post, user));

        return PostSimpleRes.from(post, fileResList);
    }


    public void changeUploadStatus(UploadStatusUpdateReq request) {
        PostFile postFile = postfileRepository.findByIdAndState(request.getFileId(), ACTIVE)
                .orElseThrow(() -> new NotFoundException(FILE_NOT_FOUND));


        if (!Objects.equals(postFile.getPostId(), request.getPostId())) {
            throw new ServicePolicyException(NOT_MATCH_POST);
        }

        if (postFile.getUploadStatus() == SUCCESS) {
            boolean isFileExist
                    = s3Service.isS3FileExist(postFile.getRegion(), postFile.getBucketName(), postFile.getFilePath());

            if (!isFileExist) {
                postFile.changeUploadStatus(FAIL);
                return;
            }
        }

        postFile.changeUploadStatus(request.getUploadStatus());
    }


    @Transactional
    public void deletePostBy(PostDeleteReq request) {

        User user = userRepository.findByIdAndState(request.getUserId(), ACTIVE)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        Post post = postRepository.findByIdAndState(request.getPostId(), ACTIVE)
                .orElseThrow(() -> new NotFoundException(POST_NOT_FOUND));

        // 게시글 삭제
        post.inActive();

        // 게시글 포함되는 사진 삭제
        postfileRepository.findByPostIdAndState(request.getPostId(), ACTIVE)
                .forEach(BaseEntity::inActive);
        // 게시글 포함되는 좋아요 삭제
        postLikeRepository.findByPostIdAndState(request.getPostId(), ACTIVE)
                .forEach(BaseEntity::inActive);

        // 게시글 신고 내역들도 삭제
        reportRepository.findByDomainIdAndDomainTypeAndState(request.getPostId(), POST, ACTIVE)
                .forEach(BaseEntity::inActive);

        // 게시글 포함되는 댓글 삭제
        List<Comment> comments = commentRepository.findByPostIdAndState(request.getPostId(), ACTIVE);
        List<Long> commentIds = comments
                .stream()
                .map(Comment::getId)
                .collect(Collectors.toList());

        // 댓글 삭제 내용도 삭제
        reportRepository.findByDomainTypeAndIdIn(COMMENT, commentIds)
                .forEach(BaseEntity::inActive);

        applicationEventPublisher.publishEvent(PostHistoryEvent.from(OperationType.DELETE, post, user));
    }


    @Transactional
    public PostLikeRes clickLike(PostLikeReq request) {

        // 조회 후 있으면 제거 없으면 추가
        Optional<PostLike> postLikeOpt
                = postLikeRepository.findByPostIdAndUserId(request.getPostId(), request.getUserId());

        if (postLikeOpt.isPresent()) {
            PostLike postLike = postLikeOpt.get();

            // 비 활성화된 좋아요가 있다면 활성화
            if (postLike.getState() == INACTIVE) {
                postLike.active();
                return PostLikeRes.from(postLike, ACTIVE);
            }

            // 좋아요 취소
            postLike.inActive();

            return PostLikeRes.from(postLike, INACTIVE);

        } else {
            // 신규 좋아요 생성
            PostLike newPostLike = PostLike.builder()
                    .postId(request.getPostId())
                    .userId(request.getUserId())
                    .build();
            postLikeRepository.save(newPostLike);

            return PostLikeRes.from(newPostLike, ACTIVE);
        }
    }


    public Page<PostDetailRes> getPostsBy(Pageable pageable) {
        Page<PostListView> postListViews = postListViewRepository.findAll(pageable);

        List<Long> userIds = postListViews
                .stream()
                .map(PostListView::getUserId)
                .collect(Collectors.toList());

        Map<Long, UserRes> usersMap = userRepository.findByIds(userIds)
                .stream()
                .map(this::getUserResWithPreSignedUrl)
                .collect(Collectors.toMap(UserRes::getUserId, userRes -> userRes));

        List<Long> postIds = postListViews
                .stream()
                .map(PostListView::getPostId)
                .collect(Collectors.toList());

        Map<Long, List<FileRes>> postMap = postfileRepository.findByPostIds(postIds)
                .stream()
                .map(this::getFileResWithPreSignedUrl)
                .collect(Collectors.groupingBy(FileRes::getPostId));

        Page<PostDetailRes> posts = postListViews.map(post -> {
            List<FileRes> filesRes = postMap.get(post.getPostId());
            UserRes userRes = usersMap.get(post.getUserId());
            return PostDetailRes.from(post, filesRes, userRes);
        });

        return posts;
    }


    public PostDetailRes getPostDetail(Long postId) { // comment 추가?
        PostListView post = postListViewRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(POST_NOT_FOUND));

        User user = userRepository.findById(post.getUserId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        PostFileBucket bucket = applicationProp.getStorage().getPostFileBucket();
        List<FileRes> postFiles = postfileRepository.findByPostIdAndState(postId, ACTIVE)
                .stream()
                .map(this::getFileResWithPreSignedUrl)
                .collect(Collectors.toList());

        URL userProfileUrl = s3Service.getDownloadUrl(user.getRegionId(), user.getBucketName(),
                user.getFilePath(), bucket.getDownloadUrlExpirationSeconds());
        UserRes userInfo = UserRes.from(user, userProfileUrl.toString());

        return PostDetailRes.from(post, postFiles, userInfo);
    }


    private FileRes getFileResWithPreSignedUrl(PostFile file) {
        URL preSignedUrl = s3Service.getDownloadUrl(file.getRegion(), file.getBucketName(),
                file.getFilePath(), applicationProp.getStorage().getPostFileBucket().getDownloadUrlExpirationSeconds());
        return FileRes.from(file, preSignedUrl.toString());
    }


    private UserRes getUserResWithPreSignedUrl(User user) {
        URL preSignedUrl = s3Service.getDownloadUrl(user.getRegionId(), user.getBucketName(),
                user.getFilePath(), applicationProp.getStorage().getPostFileBucket().getDownloadUrlExpirationSeconds());
        return UserRes.from(user, preSignedUrl.toString());
    }

}
