package com.example.demo.src.admin.model.response;

import com.example.demo.src.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@Schema(description = "관리자 유저 조회 응답")
public class AdminUserRes {
    public static AdminUserRes from(User user) {
        return null;
    }
}
