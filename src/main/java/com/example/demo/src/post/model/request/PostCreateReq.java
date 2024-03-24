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
@Schema(description = "게시글 생성 폼")
public class PostCreateReq {

    @NotBlank
    @Schema(description = "게시글 제목", example = "제목")
    private final String title;

    @NotBlank
    @Schema(description = "게시글 내용", example = "내용")
    private final String content;

    @Min(1)
    @NotNull
    @Schema(description = "게시글 작성자 ID", example = "1")
    private final Long userId;

    @Schema(description = "게시글에 첨부할 업로드 파일들")
    private final List<FileCreateReq> uploadFiles;

    @Builder
    @JsonCreator
    public PostCreateReq(
            @JsonProperty("title") String title,
            @JsonProperty("content") String content,
            @JsonProperty("userId") Long userId,
            @JsonProperty("uploadFiles") List<FileCreateReq> uploadFiles
    ) {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.uploadFiles = uploadFiles;
    }
}
