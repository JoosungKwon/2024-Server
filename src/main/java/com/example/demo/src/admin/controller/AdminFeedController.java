package com.example.demo.src.admin.controller;

import com.example.demo.common.model.response.BaseResponse;
import com.example.demo.src.admin.model.request.AdminPostSearchReq;
import com.example.demo.src.admin.model.response.AdminPostDetail;
import com.example.demo.src.admin.model.response.AdminPostRes;
import com.example.demo.src.admin.service.AdminFeedService;
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

import static com.example.demo.common.Router.Admin.Args;
import static com.example.demo.common.Router.Admin.FEEDS;
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
@RequestMapping(V1 + ADMIN + FEEDS)
public class AdminFeedController {

    private final AdminFeedService adminFeedService;

    @Operation(summary = "게시글 피드 조회")
    @GetMapping
    @ResponseStatus(OK)
    public BaseResponse<Page<AdminPostRes>> getFeeds(
            @PageableDefault @Valid Pageable pageable,
            @RequestBody @Valid AdminPostSearchReq request
    ) {
        Page<AdminPostRes> posts = adminFeedService.getPostsBy(request, pageable);

        return new BaseResponse<>(posts);
    }


    @Operation(summary = "게시글 데이터 상세 조회")
    @GetMapping(Args.POST_ID)
    @ResponseStatus(OK)
    public BaseResponse<AdminPostDetail> getPostDetailForAdmin(
            @PathVariable @NotNull @Min(0) Long postId
    ) {
        AdminPostDetail post = adminFeedService.getPostDetailForAdmin(postId);

        return new BaseResponse<>(post);
    }


}
