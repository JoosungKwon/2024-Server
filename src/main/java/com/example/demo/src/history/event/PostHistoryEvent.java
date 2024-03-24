package com.example.demo.src.history.event;

import com.example.demo.common.model.OperationType;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class PostHistoryEvent {

    private OperationType operationType;

    private Long postId;

    private Long userId;

    private String userName;

    public static PostHistoryEvent from(OperationType operationType, Post post, User user) {
        return PostHistoryEvent.builder()
                .operationType(operationType)
                .postId(post.getId())
                .userId(user.getId())
                .userName(user.getName())
                .build();
    }
}
