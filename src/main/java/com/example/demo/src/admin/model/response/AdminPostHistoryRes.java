package com.example.demo.src.admin.model.response;

import com.example.demo.src.history.entity.CommentHistory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@Schema(description = "관리자 댓글 기록 조회 응답")
public class AdminPostHistoryRes {
    public static AdminPostHistoryRes from(CommentHistory commentHistory) {
        return null;
    }
}
