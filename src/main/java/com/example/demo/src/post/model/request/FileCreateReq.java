package com.example.demo.src.post.model.request;

import com.example.demo.src.post.entity.FileType;
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
@Schema(description = "파일 생성 폼")
public class FileCreateReq {

    @NotBlank
    @Schema(description = "파일 이름", example = "test.jpg")
    private final String name;

    @NotNull
    @Schema(description = "파일 타입", example = "IMAGE")
    private final FileType type;

    @Min(1)
    @NotNull
    @Schema(description = "파일 크기(Byte)", example = "1024")
    private final Long size;


    @Builder
    @JsonCreator
    public FileCreateReq(
            @JsonProperty("name") String name,
            @JsonProperty("type") FileType type,
            @JsonProperty("size") Long size
    ) {
        this.name = name;
        this.type = type;
        this.size = size;
    }

}
