package com.example.demo.src.report.controller;

import com.example.demo.common.model.response.BaseResponse;
import com.example.demo.src.report.model.request.ReportCreateReq;
import com.example.demo.src.report.model.response.ReportRes;
import com.example.demo.src.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.demo.common.Router.Domain.REPORT;
import static com.example.demo.common.Router.V1;
import static org.springframework.http.HttpStatus.OK;

@Tag(
        name = "신고 API",
        description = "신고를 위한 API"
)
@Validated
@RestController
@RequestMapping(V1 + REPORT)
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "신고 기능")
    @PostMapping
    @ResponseStatus(OK)
    public BaseResponse<ReportRes> createReport(
            @RequestBody @Valid ReportCreateReq request
    ) {
        ReportRes reportRes = reportService.createReport(request);

        return new BaseResponse<>(reportRes);
    }


}
