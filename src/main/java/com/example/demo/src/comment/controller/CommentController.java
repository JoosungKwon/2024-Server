package com.example.demo.src.comment.controller;

import com.example.demo.common.model.response.BaseResponse;
import com.example.demo.src.comment.model.request.CommentCreateReq;
import com.example.demo.src.comment.model.request.CommentDeleteReq;
import com.example.demo.src.comment.model.request.CommentUpdateReq;
import com.example.demo.src.comment.model.response.CommentCreateRes;
import com.example.demo.src.comment.model.response.CommentDetailRes;
import com.example.demo.src.comment.model.response.CommentUpdateRes;
import com.example.demo.src.comment.service.CommentService;
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
import java.util.List;

import static com.example.demo.common.Router.Comment.Args;
import static com.example.demo.common.Router.Common.LIST;
import static com.example.demo.common.Router.Common.PAGE;
import static com.example.demo.common.Router.Domain.COMMENTS;
import static com.example.demo.common.Router.V1;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@Tag(
        name = "댓글 API 목록",
        description = "댓글 생성 및 조회 등을 관리하기 위한 API"
)
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(V1 + COMMENTS)
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 생성")
    @PostMapping
    @ResponseStatus(OK)
    public BaseResponse<CommentCreateRes> createComment(
            @RequestBody @Valid CommentCreateReq request
    ) {
        CommentCreateRes comment = commentService.createCommentBy(request);

        return new BaseResponse<>(comment);
    }

    @Operation(summary = "댓글 내용 변경")
    @PutMapping(Args.COMMENT_ID)
    @ResponseStatus(OK)
    public BaseResponse<CommentUpdateRes> updateComment(
            @PathVariable @NotNull @Min(0) Long commentId,
            @RequestParam @NotNull @Min(0) Long userId,
            @RequestBody @Valid CommentUpdateReq request
    ) {
        request.setCommentId(commentId);
        request.setUserId(userId);
        CommentUpdateRes comment = commentService.updateCommentBy(request);

        return new BaseResponse<>(comment);
    }

    @Operation(summary = "댓글 삭제")
    @PatchMapping(Args.COMMENT_ID)
    @ResponseStatus(NO_CONTENT)
    public void deleteComment(
            @PathVariable @NotNull @Min(0) Long commentId,
            @RequestParam @NotNull @Min(0) Long userId
    ) {
        CommentDeleteReq request = CommentDeleteReq.builder()
                .commentId(commentId)
                .userId(userId)
                .build();

        commentService.deleteCommentBy(request);
    }


    @Operation(summary = "댓글 전체 조회")
    @GetMapping(LIST)
    @ResponseStatus(OK)
    public BaseResponse<List<CommentDetailRes>> getCommentsByList(
            @RequestParam @NotNull @Min(0) Long postId
    ) {
        List<CommentDetailRes> comments = commentService.getCommentsBy(postId);

        return new BaseResponse<>(comments);
    }


    @Operation(summary = "댓글 페이지 조회")
    @GetMapping(PAGE)
    @ResponseStatus(OK)
    public BaseResponse<Page<CommentDetailRes>> getCommentsByPage(
            @RequestParam @NotNull @Min(0) Long postId,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable
    ) {
        Page<CommentDetailRes> comments = commentService.getCommentsBy(postId, pageable);

        return new BaseResponse<>(comments);
    }

}
