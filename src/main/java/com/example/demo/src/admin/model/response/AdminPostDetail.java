package com.example.demo.src.admin.model.response;

import com.example.demo.src.post.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@Schema(description = "관리자 게시글 상세 조회 응답")
public class AdminPostDetail {
    public static AdminPostDetail from(Post post) {
        return null;
    }
}
