package com.example.demo.src.post.model.response;

import com.example.demo.src.post.entity.PostFile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@Schema(description = "파일 정보 데이터")
public class FileRes {

    @Schema(description = "파일 ID", example = "1")
    private final Long fileId;

    @Schema(description = "파일 Path", example = "joosung/image/fileName")
    private final String filePath;

    @Schema(description = "파일 업로드 URL")
    private final String fileUploadUrl;

    @Schema(description = "게시글 ID", example = "1")
    private final Long postId;

    public static FileRes from(PostFile file, String fileUploadUrl) {
        return FileRes.builder()
                .fileId(file.getId())
                .filePath(file.getFilePath())
                .fileUploadUrl(fileUploadUrl)
                .postId(file.getPostId())
                .build();
    }
}
