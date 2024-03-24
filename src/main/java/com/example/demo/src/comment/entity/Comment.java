package com.example.demo.src.comment.entity;

import com.example.demo.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    public static final int CONTENT_MAX_SIZE = 255;
    public static final int NICKNAME_MAX_SIZE = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "content", length = CONTENT_MAX_SIZE, nullable = false)
    private String content;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "postId")
    private Long postId;


    @Builder
    public Comment(String content, Long userId, Long postId) {
        this.content = validateContent(content);
        this.postId = postId;
        this.userId = userId;
    }

    public void updateContent(String content) {
        this.content = validateContent(content);
    }

    private String validateContent(String content) {
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("댓글의 본문이 비어 있을 수 없습니다.");
        }

        if (content.length() > CONTENT_MAX_SIZE) {
            throw new IllegalArgumentException("댓글의 본문은" + CONTENT_MAX_SIZE + "글자를 초과할 수 없습니다.");
        }
        return content;
    }
}