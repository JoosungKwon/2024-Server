package com.example.demo.src.admin.controller;

import com.example.demo.common.model.response.BaseResponse;
import com.example.demo.src.admin.model.request.AdminHistorySearchReq;
import com.example.demo.src.admin.model.response.AdminCommentHistoryRes;
import com.example.demo.src.admin.model.response.AdminPostHistoryRes;
import com.example.demo.src.admin.model.response.AdminUserHistoryRes;
import com.example.demo.src.admin.service.AdminHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.demo.common.Router.Admin.HISTORIES;
import static com.example.demo.common.Router.Domain.*;
import static com.example.demo.common.Router.V1;
import static org.springframework.http.HttpStatus.OK;

@Tag(
        name = "관리자 기능 API 목록",
        description = "관리자가 데이터를 관리하기 위한 API 목록"
)
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(V1 + ADMIN + HISTORIES)
public class AdminHistoryController {

    private final AdminHistoryService adminHistoryService;

    @Operation(summary = "게시글 CUD 기록 조회")
    @GetMapping(POSTS)
    @ResponseStatus(OK)
    public BaseResponse<Page<AdminPostHistoryRes>> getPostHistory(
            @PageableDefault Pageable pageable,
            @RequestBody @Valid AdminHistorySearchReq request
    ) {
        Page<AdminPostHistoryRes> postHistories = adminHistoryService.getPostHistories(request, pageable);

        return new BaseResponse<>(postHistories);
    }


    @Operation(summary = "댓글 CUD 기록 조회")
    @GetMapping(COMMENTS)
    @ResponseStatus(OK)
    public BaseResponse<Page<AdminCommentHistoryRes>> getCommentHistory(
            @PageableDefault Pageable pageable,
            @RequestBody @Valid AdminHistorySearchReq request
    ) {
        Page<AdminCommentHistoryRes> commentHistories = adminHistoryService.getCommentHistories(request, pageable);

        return new BaseResponse<>(commentHistories);
    }


    @Operation(summary = "유저 CUD 기록 조회")
    @GetMapping(USERS)
    @ResponseStatus(OK)
    public BaseResponse<Page<AdminUserHistoryRes>> getUserHistory(
            @PageableDefault Pageable pageable,
            @RequestBody @Valid AdminHistorySearchReq request
    ) {
        Page<AdminUserHistoryRes> userHistories = adminHistoryService.getUserHistories(request, pageable);

        return new BaseResponse<>(userHistories);
    }

}
