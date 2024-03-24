package com.example.demo.src.report.model.response;

import com.example.demo.src.report.entity.DomainType;
import com.example.demo.src.report.entity.Report;
import com.example.demo.src.report.entity.ReportType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@Schema(description = "신고 결과 데이터")
public class ReportRes {

    @Schema(description = "신고 ID", example = "1")
    private Long reportId;

    @Schema(description = "신고 타입", example = "VIOLENCE")
    private ReportType reportType;

    @Schema(description = "신고 대상 도메인", example = "POST")
    private DomainType domainType;

    @Schema(description = "신고 대상 ID", example = "1")
    private Long domainId;

    @Schema(description = "신고자 ID", example = "1")
    private Long userId;


    public static ReportRes from(Report report) {
        return ReportRes.builder()
                .reportId(report.getId())
                .reportType(report.getReportType())
                .domainType(report.getDomainType())
                .domainId(report.getDomainId())
                .userId(report.getUserId())
                .build();
    }
}
