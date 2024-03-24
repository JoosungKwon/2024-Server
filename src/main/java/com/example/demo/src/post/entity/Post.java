package com.example.demo.src.post.entity;

import com.example.demo.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "post")
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    public static final int TITLE_MAX_SIZE = 30;
    public static final int CONTENT_MAX_SIZE = 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "title", length = TITLE_MAX_SIZE, nullable = false)
    private String title;

    @NotNull
    @Column(name = "content", length = CONTENT_MAX_SIZE, nullable = false)
    private String content;

    @NotNull
    @Column(name = "userId", nullable = false)
    private Long userId;

    private int isVisibleLike; // 좋아요 수 공개 여부 0: 비공개, 1: 공개


    @Builder
    public Post(String title, String content, Long userId) {
        this.title = validateTitle(title);
        this.content = validateContent(content);
        this.userId = userId;
    }

    public void changeTitle(String title) {
        this.title = validateTitle(title);
    }

    public void changeContent(String title) {
        this.content = validateContent(content);
    }

    private String validateTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("게시글의 제목이 비어 있을 수 없습니다.");
        }

        if (title.length() > TITLE_MAX_SIZE) {
            throw new IllegalArgumentException("게시글의 제목은" + TITLE_MAX_SIZE + "글자를 초과할 수 없습니다.");
        }

        return title;
    }

    private String validateContent(String content) {
        if (content.length() > CONTENT_MAX_SIZE) {
            throw new IllegalArgumentException("게시글의 내용은" + CONTENT_MAX_SIZE + "글자를 초과할 수 없습니다.");
        }

        return content;
    }

    public void inActive() {
        this.state = State.INACTIVE;
    }
}