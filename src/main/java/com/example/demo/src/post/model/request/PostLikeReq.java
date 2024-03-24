package com.example.demo.src.post.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@Schema(description = "게시글 좋아요 요청 처리 폼", hidden = true)
public class PostLikeReq {

    @Schema(hidden = true)
    private Long postId;

    @Schema(hidden = true)
    private Long userId;

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public void setUserId(Long postId) {
        this.postId = postId;
    }

}
