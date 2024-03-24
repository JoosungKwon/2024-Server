package com.example.demo.src.admin.controller;

import com.example.demo.common.model.response.BaseResponse;
import com.example.demo.src.admin.model.request.AdminUserSearchReq;
import com.example.demo.src.admin.model.response.AdminUserRes;
import com.example.demo.src.admin.service.AdminUserManageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.demo.common.Router.Admin.USERS;
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
@RequestMapping(V1 + ADMIN + USERS)
public class AdminUserManageController {

    private final AdminUserManageService adminUserManageService;


    @Operation(summary = "유저 검색")
    @GetMapping
    @ResponseStatus(OK)
    public BaseResponse<Page<AdminUserRes>> getUsers(
            @PageableDefault Pageable pageable,
            @RequestBody @Valid AdminUserSearchReq request
    ) {
        Page<AdminUserRes> reportRes = adminUserManageService.getUsersBy(request, pageable);

        return new BaseResponse<>(reportRes);
    }

}
