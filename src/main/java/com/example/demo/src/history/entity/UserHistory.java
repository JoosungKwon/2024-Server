package com.example.demo.src.history.entity;

import com.example.demo.common.model.OperationType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "user_history")
public class UserHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    private Long userId;

    @CreationTimestamp
    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public UserHistory(Long userId, OperationType operationType) {
        this.userId = userId;
        this.operationType = operationType;
    }
}
