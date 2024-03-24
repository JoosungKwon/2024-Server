package com.example.demo.src.comment.model.response;

import com.example.demo.src.comment.entity.view.CommentListView;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@Schema(description = "댓글 상세 정보")
public class CommentDetailRes {

    @Schema(description = "댓글 ID", example = "1")
    private Long commentId;

    @Schema(description = "유저 ID", example = "1")
    private Long userId;

    @Schema(description = "유저 이름", example = "홍길동")
    private String userName;

    @Schema(description = "유저 프로필 이미지 URL", example = "http://localhost:8080/files/1")
    private String profileImageUrl;

    @Schema(description = "게시글 ID", example = "1")
    private Long postId;

    @Schema(description = "댓글 내용", example = "댓글 내용")
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Seoul")
    @Schema(description = "댓글 생성일시", example = "2022-08-01 00:00:00")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Seoul")
    @Schema(description = "댓글 수정일시", example = "2022-08-01 00:00:00")
    private LocalDateTime updatedAt;


    public static CommentDetailRes from(CommentListView commentListView, String preSignedProfileImageUrl) {
        return CommentDetailRes.builder()
                .commentId(commentListView.getCommentId())
                .userId(commentListView.getUserId())
                .userName(commentListView.getUserName())
                .profileImageUrl(preSignedProfileImageUrl)
                .postId(commentListView.getPostId())
                .content(commentListView.getContent())
                .createdAt(commentListView.getCreatedAt())
                .updatedAt(commentListView.getUpdatedAt())
                .build();
    }
}
