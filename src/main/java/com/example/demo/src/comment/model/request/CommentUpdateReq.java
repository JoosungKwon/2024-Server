package com.example.demo.src.comment.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;


@Getter
@ToString
@Schema(description = "댓글 수정 폼")
public class CommentUpdateReq {

    @NotBlank
    @Schema(description = "댓글 내용", example = "댓글 내용")
    private final String content;

    @Schema(hidden = true)
    private Long commentId;

    @Schema(hidden = true)
    private Long userId;

    @Builder
    @JsonCreator
    public CommentUpdateReq(
            @JsonProperty("content") String content
    ) {
        this.content = content;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
