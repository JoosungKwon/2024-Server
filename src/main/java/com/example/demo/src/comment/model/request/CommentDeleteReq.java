package com.example.demo.src.comment.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Schema(description = "댓글 삭제 폼")
public class CommentDeleteReq {

    @Schema(hidden = true)
    private final Long commentId;

    @Schema(hidden = true)
    private final Long userId;

    @Builder
    @JsonCreator
    public CommentDeleteReq(
            @JsonProperty("commentId") Long commentId,
            @JsonProperty("userId") Long userId
    ) {
        this.commentId = commentId;
        this.userId = userId;
    }
}
