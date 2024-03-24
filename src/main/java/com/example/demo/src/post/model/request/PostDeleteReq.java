package com.example.demo.src.post.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@Schema(description = "게시글 삭제 폼", hidden = true)
public class PostDeleteReq {

    @Schema(hidden = true)
    private Long postId;

    @Schema(hidden = true)
    private Long userId;

}
