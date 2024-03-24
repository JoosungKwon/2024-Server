package com.example.demo.src.post.controller;

import com.example.demo.common.model.response.BaseResponse;
import com.example.demo.src.post.model.request.*;
import com.example.demo.src.post.model.response.PostDetailRes;
import com.example.demo.src.post.model.response.PostLikeRes;
import com.example.demo.src.post.model.response.PostSimpleRes;
import com.example.demo.src.post.service.PostService;
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

import static com.example.demo.common.Router.Domain.POSTS;
import static com.example.demo.common.Router.Post.*;
import static com.example.demo.common.Router.V1;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@Tag(
        name = "게시글 API 목록",
        description = "게시글 생성 및 조회 등을 관리하기 위한 API"
)
@Validated
@RestController
@RequestMapping(V1 + POSTS)
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(summary = "게시글 생성")
    @PostMapping
    @ResponseStatus(OK)
    public BaseResponse<PostSimpleRes> createPost(
            @RequestBody @Valid PostCreateReq request
    ) {
        PostSimpleRes post = postService.createPostBy(request);

        return new BaseResponse<>(post);
    }


    @Operation(summary = "게시글 내용 변경")
    @PutMapping(Args.POST_ID)
    @ResponseStatus(OK)
    public BaseResponse<PostSimpleRes> updatePostInfo(
            @PathVariable @NotNull @Min(0) Long postId,
            @RequestBody @Valid PostUpdateReq request
    ) {
        request.setPostId(postId);
        PostSimpleRes post = postService.updatePostBy(request);

        return new BaseResponse<>(post);
    }


    @Operation(summary = "게시글 파일 업로드 상태 변경")
    @PostMapping(Args.POST_ID + FILES + Args.FILE_ID + UPLOAD_STATUS)
    @ResponseStatus(OK)
    public void changeUploadStatus(
            @PathVariable @NotNull @Min(0) Long postId,
            @PathVariable @NotNull @Min(0) Long fileId,
            @RequestBody @Valid UploadStatusUpdateReq request
    ) {
        request.setPostId(postId);
        request.setFileId(fileId);
        postService.changeUploadStatus(request);
    }


    @Operation(summary = "게시글 삭제")
    @PatchMapping(Args.POST_ID)
    @ResponseStatus(NO_CONTENT)
    public void deletePost(
            @PathVariable @NotNull @Min(0) Long postId,
            @RequestParam @NotNull @Min(0) Long userId
    ) {
        PostDeleteReq request = PostDeleteReq.builder()
                .postId(postId)
                .userId(userId)
                .build();
        postService.deletePostBy(request);
    }


    @Operation(summary = "게시글 좋아요", description = "좋아요를 누르면 좋아요가 증가하고, 이미 누른 상태에서 다시 누르면 좋아요가 취소됩니다.")
    @PostMapping(Args.POST_ID + LIKE)
    @ResponseStatus(OK)
    public BaseResponse<PostLikeRes> clickLike(
            @PathVariable @NotNull @Min(0) Long postId,
            @RequestParam @NotNull @Min(0) Long userId
    ) {
        PostLikeReq request = PostLikeReq.builder()
                .postId(postId)
                .userId(userId)
                .build();

        PostLikeRes postLikeRes = postService.clickLike(request);

        return new BaseResponse<>(postLikeRes);
    }


    @Operation(summary = "게시글 페이지 목록 조회")
    @GetMapping
    @ResponseStatus(OK)
    public BaseResponse<Page<PostDetailRes>> getPostsByPage(
            @PageableDefault Pageable pageable
    ) {
        Page<PostDetailRes> posts = postService.getPostsBy(pageable);

        return new BaseResponse<>(posts);
    }

    @Operation(summary = "게시글 상세 조회")
    @GetMapping(Args.POST_ID)
    @ResponseStatus(OK)
    public BaseResponse<PostDetailRes> getPostDetail(
            @PathVariable @NotNull @Min(0) Long postId
    ) {
        PostDetailRes postDetail = postService.getPostDetail(postId);

        return new BaseResponse<>(postDetail);
    }

}
