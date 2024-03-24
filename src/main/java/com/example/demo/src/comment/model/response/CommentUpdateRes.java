package com.example.demo.src.comment.model.response;

import com.example.demo.src.comment.entity.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@Schema(description = "댓글 수정 결과")
public class CommentUpdateRes {

    @Schema(description = "댓글 ID", example = "1")
    private Long commentId;

    @Schema(description = "유저 ID", example = "1")
    private Long userId;

    @Schema(description = "게시글 ID", example = "1")
    private Long postId;

    @Schema(description = "댓글 내용", example = "댓글 내용")
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Seoul")
    @Schema(description = "댓글 수정일시", example = "2022-08-01 00:00:00")
    private LocalDateTime updatedAt;


    public static CommentUpdateRes from(Comment comment) {
        return CommentUpdateRes.builder()
                .commentId(comment.getId())
                .userId(comment.getUserId())
                .postId(comment.getPostId())
                .content(comment.getContent())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }

}
