package com.example.demo.src.post.entity;

import com.example.demo.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "post_like")
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "postId")
    private Long postId;

    @Column(name = "userId")
    private Long userId;

    @Builder
    public PostLike(Long postId, Long userId) {
        this.postId = postId;
        this.userId = userId;
    }

    public void inActive() {
        this.state = State.INACTIVE;
    }

    public void active() {
        this.state = State.ACTIVE;
    }
}
