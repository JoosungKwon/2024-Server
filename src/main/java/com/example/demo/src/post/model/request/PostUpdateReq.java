package com.example.demo.src.post.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@ToString
@Schema(description = "게시글 수정 폼")
public class PostUpdateReq {

    @Min(0)
    @NotNull
    @Schema(description = "게시글 작성자 ID", example = "1")
    private final Long userId;

    @NotBlank
    @Schema(description = "게시글 제목", example = "제목")
    private final String title;

    @NotBlank
    @Schema(description = "게시글 내용", example = "내용")
    private final String content;

    @Schema(hidden = true)
    private Long postId;

    @Schema(description = "게시글에 추가할 업로드 파일들")
    private final List<FileCreateReq> newUploadFiles;

    @Schema(description = "게시글에서 삭제할 파일들")
    private final List<FileDeleteReq> deleteFiles;


    @Builder
    @JsonCreator
    public PostUpdateReq(
            @JsonProperty("userId") Long userId,
            @JsonProperty("title") String title,
            @JsonProperty("content") String content,
            @JsonProperty("newUploadFiles") List<FileCreateReq> newUploadFiles,
            @JsonProperty("deleteFiles") List<FileDeleteReq> deleteFiles
    ) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.newUploadFiles = newUploadFiles;
        this.deleteFiles = deleteFiles;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

}
