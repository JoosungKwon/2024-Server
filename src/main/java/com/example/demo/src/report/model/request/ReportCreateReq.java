package com.example.demo.src.report.model.request;

import com.example.demo.src.report.entity.DomainType;
import com.example.demo.src.report.entity.ReportType;
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
@Schema(description = "신고 요청 폼")
public class ReportCreateReq {

    @NotNull
    @Schema(description = "신고 타입", example = "VIOLENCE")
    private final ReportType reportType;

    @NotNull
    @Schema(description = "신고 대상 도메인", example = "POST")
    private final DomainType domainType;

    @Min(0)
    @NotNull
    @Schema(description = "신고 대상 ID", example = "1")
    private final Long domainId;

    @Min(0)
    @NotNull
    @Schema(description = "신고자 ID", example = "1")
    private final Long userId;

    @Builder
    @JsonCreator
    public ReportCreateReq(
            @JsonProperty("reportType") ReportType reportType,
            @JsonProperty("domainType") DomainType domainType,
            @JsonProperty("domainId") Long domainId,
            @JsonProperty("userId") Long userId
    ) {
        this.reportType = reportType;
        this.domainType = domainType;
        this.domainId = domainId;
        this.userId = userId;
    }
}
