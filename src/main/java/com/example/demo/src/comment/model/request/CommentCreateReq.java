package com.example.demo.src.comment.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@ToString
@Schema(description = "댓글 생성 폼")
public class CommentCreateReq {

    @NotBlank
    @Schema(description = "댓글 내용", example = "댓글 내용")
    private final String content;

    @Min(0)
    @NotNull
    @Schema(description = "댓글 작성자 ID", example = "1")
    private final Long userId;

    @Min(0)
    @NotNull
    @Schema(description = "댓글이 달릴 게시글 ID", example = "1")
    private final Long postId;

    @Builder
    @JsonCreator
    public CommentCreateReq(
            @JsonProperty("content") String content,
            @JsonProperty("userId") Long userId,
            @JsonProperty("postId") Long postId
    ) {
        this.content = content;
        this.userId = userId;
        this.postId = postId;
    }

}
