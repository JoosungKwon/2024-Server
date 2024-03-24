package com.example.demo.src.admin.controller;

import com.example.demo.common.model.response.BaseResponse;
import com.example.demo.src.admin.model.request.AdminBlockReq;
import com.example.demo.src.admin.model.request.adminReportSearchReq;
import com.example.demo.src.admin.service.AdminReportService;
import com.example.demo.src.report.model.response.ReportRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static com.example.demo.common.Router.Admin.REPORTS;
import static com.example.demo.common.Router.Domain.ADMIN;
import static com.example.demo.common.Router.V1;
import static org.springframework.http.HttpStatus.OK;

@Tag(
        name = "관리자 기능 API 목록",
        description = "관리자가 데이터를 관리하기 위한 API 목록"
)
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(V1 + ADMIN + REPORTS)
public class AdminReportController {

    private final AdminReportService adminReportService;


    @Operation(summary = "신고 내역 조회")
    @GetMapping
    @ResponseStatus(OK)
    public BaseResponse<Page<ReportRes>> getReports(
            @PageableDefault Pageable pageable,
            @RequestBody @Valid adminReportSearchReq request
    ) {
        Page<ReportRes> reportRes = adminReportService.getReportsBy(request, pageable);

        return new BaseResponse<>(reportRes);
    }


    @Operation(summary = "게시글 및 댓글 차단")
    @PostMapping
    @ResponseStatus(OK)
    public BaseResponse<ReportRes> changeStateToBlock(
            @RequestBody @Valid AdminBlockReq request
    ) {
        ReportRes reportRes = adminReportService.changeStateToBlock(request);

        return new BaseResponse<>(reportRes);
    }


    @Operation(summary = "신고 내역 삭제")
    @DeleteMapping
    @ResponseStatus(OK)
    public void deleteReport(
            @RequestBody @NotNull @Min(0) Long reportId
    ) {
        adminReportService.deleteReport(reportId);
    }

}
