package com.example.demo.src.admin.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@Schema(description = "관리자 신고 검색 응답")
public class AdminReportRes {
}
