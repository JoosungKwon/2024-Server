package com.example.demo.src.admin.model.response;

import com.example.demo.src.history.entity.PostHistory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@Schema(description = "관리자 댓글 기록 조회 응답")
public class AdminCommentHistoryRes {
    public static AdminCommentHistoryRes from(PostHistory postHistory) {
        return null;
    }
}
