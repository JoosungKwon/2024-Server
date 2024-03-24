package com.example.demo.src.history.entity;

import com.example.demo.common.model.OperationType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "comment_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    private Long commentId;

    private Long postId;

    private Long userId;

    private String userName;

    @CreationTimestamp
    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public CommentHistory(OperationType operationType, Long commentId, Long postId, Long userId, String userName) {
        this.operationType = operationType;
        this.commentId = commentId;
        this.postId = postId;
        this.userId = userId;
        this.userName = userName;
    }
}