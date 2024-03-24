package com.example.demo.src.history.event;

import com.example.demo.common.model.OperationType;
import com.example.demo.src.comment.entity.Comment;
import com.example.demo.src.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class CommentHistoryEvent {

    private OperationType operationType;

    private Long commentId;

    private Long postId;

    private Long userId;

    private String userName;

    public static CommentHistoryEvent from(OperationType operationType, Comment comment, User user) {
        return CommentHistoryEvent.builder()
                .operationType(operationType)
                .commentId(comment.getId())
                .postId(comment.getPostId())
                .userId(comment.getUserId())
                .userName(user.getName())
                .build();
    }
}
