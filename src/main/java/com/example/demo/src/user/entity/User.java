package com.example.demo.src.user.entity;

import com.example.demo.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity // 필수, Class 를 Database Table화 해주는 것이다
@Table(name = "user") // Table 이름을 명시해주지 않으면 class 이름을 Table 이름으로 대체한다.
public class User extends BaseEntity {

    @Id // PK를 의미하는 어노테이션
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    private boolean isOAuth;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    private String regionId;

    private String bucketName;

    private String filePath;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    private LocalDateTime dateLatestLogin;

    @Builder
    public User(Long id, String email, String password, String name, boolean isOAuth, LoginType loginType, LocalDateTime dateLatestLogin) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.isOAuth = isOAuth;
        this.loginType = loginType;
        this.dateLatestLogin = dateLatestLogin;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateLastLogin() {
        this.dateLatestLogin = LocalDateTime.now();
    }

    public void deleteUser() {
        this.state = State.INACTIVE;
    }

}
