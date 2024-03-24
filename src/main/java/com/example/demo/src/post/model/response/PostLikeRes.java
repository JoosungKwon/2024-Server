package com.example.demo.src.post.model.response;

import com.example.demo.src.post.entity.PostLike;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import static com.example.demo.common.entity.BaseEntity.State;

@Getter
@Builder
@ToString
@Schema(description = "게시글 좋아요 요청 처리 결과")
public class PostLikeRes {

    @Schema(example = "1", description = "좋아요 상태")
    private State state;

    @Schema(example = "1", description = "게시글 ID")
    private Long postId;

    @Schema(example = "1", description = "유저 ID")
    private Long userId;

    public static PostLikeRes from(PostLike postLike, State state) {
        return PostLikeRes.builder()
                .state(state)
                .postId(postLike.getPostId())
                .userId(postLike.getUserId())
                .build();
    }
}
