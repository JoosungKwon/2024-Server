package com.example.demo.src.history.event;

import com.example.demo.common.model.OperationType;
import com.example.demo.src.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class UserHistoryEvent {

    private OperationType operationType;

    private Long userId;

    private String userName;

    public static UserHistoryEvent from(OperationType operationType, User user) {
        return UserHistoryEvent.builder()
                .operationType(operationType)
                .userId(user.getId())
                .userName(user.getName())
                .build();
    }

}
