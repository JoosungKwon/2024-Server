package com.example.demo.src.post.model.response;

import com.example.demo.src.post.entity.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "게시글 정보")
public class PostSimpleRes {

    @Schema(example = "1", description = "게시글 ID")
    private final Long postId;

    @Schema(example = "1", description = "유저 ID")
    private final Long userId;

    @Schema(example = "content", description = "게시글 내용")
    private final String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Seoul")
    @Schema(example = "2021-01-01 00:00:00", description = "게시글 생성일")
    private final String createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Seoul")
    @Schema(example = "2021-01-01 00:00:00", description = "게시글 수정일")
    private final String updatedAt;

    @Schema(example = "1", description = "게시글 사진")
    private final List<FileRes> postFile;


    public static PostSimpleRes from(Post post) {
        return from(post, null);
    }

    public static PostSimpleRes from(Post post, List<FileRes> postFile) {
        return PostSimpleRes.builder()
                .postId(post.getId())
                .userId(post.getUserId())
                .content(post.getContent())
                .createdAt(post.getCreatedAt().toString())
                .updatedAt(post.getUpdatedAt().toString())
                .postFile(postFile)
                .build();
    }

}
