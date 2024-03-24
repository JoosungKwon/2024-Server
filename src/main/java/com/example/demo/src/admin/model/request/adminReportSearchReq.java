package com.example.demo.src.admin.model.request;

import com.example.demo.src.report.entity.DomainType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Schema(description = "관리자 신고 검색 요청")
public class adminReportSearchReq {

    @Schema(description = "도메인 아이디")
    private final Long domainId;

    @Schema(description = "도메인 타입")
    private final DomainType domainType;

    @Schema(description = "시작일")
    private final LocalDateTime start;

    @Schema(description = "종료일")
    private final LocalDateTime end;

    @Builder
    @JsonCreator
    public adminReportSearchReq(
            @JsonProperty("domainId") Long domainId,
            @JsonProperty("domainType") DomainType domainType,
            @JsonProperty("start") LocalDateTime start,
            @JsonProperty("end") LocalDateTime end
    ) {
        this.domainId = domainId;
        this.domainType = domainType;
        this.start = start;
        this.end = end;
    }

}
