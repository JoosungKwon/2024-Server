package com.example.demo.src.admin.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Schema(description = "관리자 게시글 검색 요청")
public class AdminPostSearchReq {

    @Schema(description = "도메인 아이디")
    private final Long postId;

    @Schema(description = "시작일")
    private final LocalDateTime start;

    @Schema(description = "종료일")
    private final LocalDateTime end;

    @Builder
    @JsonCreator
    public AdminPostSearchReq(
            @JsonProperty("postId") Long postId,
            @JsonProperty("start") LocalDateTime start,
            @JsonProperty("end") LocalDateTime end
    ) {
        this.postId = postId;
        this.start = start;
        this.end = end;
    }
}
