package com.example.demo.src.post.model.request;

import com.example.demo.src.post.entity.UploadStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@ToString
@Schema(description = "업로드 상태 변경 폼")
public class UploadStatusUpdateReq {

    @Schema(hidden = true)
    private Long postId;

    @Schema(hidden = true)
    private Long fileId;

    @NotNull
    @Schema(description = "변경할 업로드 상태", example = "UPLOADED")
    private final UploadStatus uploadStatus;

    @Builder
    @JsonCreator
    public UploadStatusUpdateReq(
            @JsonProperty("uploadStatus") UploadStatus uploadStatus
    ) {
        this.uploadStatus = uploadStatus;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

}
