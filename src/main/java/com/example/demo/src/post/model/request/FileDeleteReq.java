package com.example.demo.src.post.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@ToString
@Schema(description = "파일 삭제 폼")
public class FileDeleteReq {

    @Min(1)
    @NotNull
    @Schema(description = "파일 ID", example = "1")
    private final Long fileId;

    @Builder
    @JsonCreator
    public FileDeleteReq(
            @JsonProperty("fileId") Long fileId
    ) {
        this.fileId = fileId;
    }

}
