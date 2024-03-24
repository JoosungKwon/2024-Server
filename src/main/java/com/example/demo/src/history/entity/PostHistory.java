package com.example.demo.src.history.entity;

import com.example.demo.common.model.OperationType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "post_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long postId;

    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    private Long userId;

    private String userName;

    @CreationTimestamp
    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public PostHistory(Long postId, OperationType operationType, Long userId, String userName) {
        this.postId = postId;
        this.operationType = operationType;
        this.userId = userId;
        this.userName = userName;
    }

}
