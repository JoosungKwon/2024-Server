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
@Schema(description = "관리자 유저 검색 요청")
public class AdminUserSearchReq {

    @Schema(description = "도메인 아이디")
    private final Long domainId;

    @Schema(description = "시작일")
    private final LocalDateTime start;

    @Schema(description = "종료일")
    private final LocalDateTime end;

    @Builder
    @JsonCreator
    public AdminUserSearchReq(
            @JsonProperty("domainId") Long domainId,
            @JsonProperty("start") LocalDateTime start,
            @JsonProperty("end") LocalDateTime end
    ) {
        this.domainId = domainId;
        this.start = start;
        this.end = end;
    }

}
