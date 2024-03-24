package com.example.demo.src.admin.model.response;

import com.example.demo.src.history.entity.UserHistory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@Schema(description = "관리자 유저 기록 조회 응답")
public class AdminUserHistoryRes {

    public static AdminUserHistoryRes from(UserHistory userHistory) {
        return null;
    }
}
