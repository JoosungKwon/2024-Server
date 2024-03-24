package com.example.demo.src.comment.entity.view;


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
@Table(name = "v_comment_list") // View 이름
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentListView {

    @Id
    private Long commentId;
    private Long userId;
    private String userName;
    private String userFilePath;
    private Long postId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
