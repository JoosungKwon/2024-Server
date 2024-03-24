package com.example.demo.src.post.model.response;

import com.example.demo.src.post.entity.view.PostListView;
import com.example.demo.src.user.UserRes;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@ToString
@Schema(description = "게시글 상세 정보")
public class PostDetailRes {

    @Schema(example = "1", description = "게시글 ID")
    private Long postId;

    @Schema(example = "1", description = "유저 ID")
    private Long userId;

    @Schema(example = "user", description = "유저 이름")
    private String userName;

    @Schema(example = "user.png", description = "유저 프로필 사진 경로")
    private String userProfileImageUrl;

    @Schema(example = "joosung/user.png", description = "유저 저장소 경로")
    private String userFilePath;

    @Schema(example = "content", description = "게시글 내용")
    private String content;

    @Schema(example = "0", description = "좋아요 수")
    private Integer likeCount;

    @Schema(example = "0", description = "댓글 수")
    private Integer commentCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Seoul")
    @Schema(example = "2021-01-01T00:00:00", description = "게시글 생성일")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Seoul")
    @Schema(example = "2021-01-01 00:00:00", description = "게시글 수정일")
    private LocalDateTime updatedAt;

    @Schema(example = "1", description = "게시글 사진")
    private List<FileRes> postFile;


    public static PostDetailRes from(PostListView post, List<FileRes> postFile, UserRes user) {
        return PostDetailRes.builder()
                .postId(post.getPostId())
                .userId(post.getUserId())
                .userName(post.getUserName())
                .userFilePath(post.getUserFilePath())
                .userProfileImageUrl(user.getProfileImageUrl())
                .content(post.getContent())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .postFile(postFile)
                .build();
    }
}
