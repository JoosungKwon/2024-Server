package com.example.demo.src.post.entity.view;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Entity
@Immutable
@Table(name = "v_post_list") // View 이름
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostListView {

    @Id
    private Long postId;
    private Long userId;
    private String userName;
    private String userFilePath;
    private String content;
    private Integer likeCount;
    private Integer commentCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
