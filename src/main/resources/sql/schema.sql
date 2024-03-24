CREATE TABLE IF NOT EXISTS user
(
    id              bigint auto_increment primary key,
    createdAt       timestamp    not null,
    state           varchar(16)  not null,
    updatedAt       timestamp    not null,
    email           varchar(128) not null,
    isOAuth         bit          not null,
    filePath        varchar(255) null,
    name            varchar(32)  not null,
    password        varchar(255) not null,
    userStatus      varchar(16)  not null,
    regionId        varchar(255) null,
    bucketName      varchar(255) null,
    loginType       varchar(16)  not null,
    dateLatestLogin timestamp    null
) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4;

CREATE OR REPLACE INDEX idx_user_ids ON user (name);
CREATE OR REPLACE INDEX idx_user_ids ON user (email);


CREATE TABLE IF NOT EXISTS comment
(
    id        bigint auto_increment primary key,
    createdAt timestamp    not null,
    state     varchar(16)  not null,
    updatedAt timestamp    not null,
    content   varchar(255) not null,
    postId    bigint       null,
    userId    bigint       null
) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4;

CREATE OR REPLACE INDEX idx_comment_postId ON comment (postId);
CREATE OR REPLACE INDEX idx_comment_userId ON comment (userId);


CREATE TABLE IF NOT EXISTS comment_history
(
    id            bigint auto_increment primary key,
    commentId     bigint       null,
    createdAt     timestamp    not null,
    operationType varchar(32)  null,
    postId        bigint       null,
    userId        bigint       null,
    userName      varchar(255) null
) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4;

CREATE OR REPLACE INDEX idx_comment_history_postId ON comment_history (postId);
CREATE OR REPLACE INDEX idx_comment_history_userId ON comment_history (userId);
CREATE OR REPLACE INDEX idx_comment_history_operationType ON comment_history (operationType);


CREATE TABLE IF NOT EXISTS post
(
    id            bigint auto_increment primary key,
    createdAt     timestamp     not null,
    state         varchar(16)   not null,
    updatedAt     timestamp     not null,
    content       varchar(1024) not null,
    isVisibleLike int           not null,
    title         varchar(64)   not null,
    userId        bigint        not null
) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4;

CREATE OR REPLACE INDEX idx_post_userId ON post (userId);


CREATE TABLE IF NOT EXISTS post_file
(
    id           bigint auto_increment primary key,
    createdAt    timestamp    not null,
    state        varchar(16)  not null,
    updatedAt    timestamp    not null,
    bucketName   varchar(255) null,
    path         varchar(255) not null,
    fileName     varchar(128) not null,
    postId       bigint       not null,
    region       varchar(255) null,
    size         bigint       not null,
    type         varchar(16)  not null,
    uploadStatus varchar(16)  null,
    userId       bigint       null,
    uuid         varchar(36)  not null
) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4;

CREATE OR REPLACE INDEX idx_post_file_postId ON post_file (postId);
CREATE OR REPLACE INDEX idx_post_file_userId ON post_file (userId);


CREATE TABLE IF NOT EXISTS post_history
(
    id            bigint auto_increment primary key,
    createdAt     timestamp    not null,
    operationType varchar(32)  null,
    postId        bigint       null,
    userId        bigint       null,
    userName      varchar(255) null
) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4;

CREATE OR REPLACE INDEX idx_post_history_postId ON post_history (postId);
CREATE OR REPLACE INDEX idx_post_history_userId ON post_history (userId);
CREATE OR REPLACE INDEX idx_post_history_operationType ON post_history (operationType);


CREATE TABLE IF NOT EXISTS post_like
(
    id        bigint auto_increment primary key,
    createdAt timestamp   not null,
    state     varchar(16) not null,
    updatedAt timestamp   not null,
    postId    bigint      null,
    userId    bigint      null
) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4;

CREATE OR REPLACE INDEX idx_post_like_postId ON post_like (postId);
CREATE OR REPLACE INDEX idx_post_like_userId ON post_like (userId);


CREATE TABLE IF NOT EXISTS report
(
    id          bigint auto_increment primary key,
    createdAt   timestamp   not null,
    state       varchar(16) not null,
    updatedAt   timestamp   not null,
    domain_id   bigint      not null,
    domain_type varchar(32) not null,
    report_type varchar(64) not null,
    user_id     bigint      not null
) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4;

CREATE OR REPLACE INDEX idx_report_domain ON report (domain_type, domain_id);
CREATE OR REPLACE INDEX idx_report_type ON report (report_type);
CREATE OR REPLACE INDEX idx_report_userId ON report (user_id);


-- VIEW
CREATE OR REPLACE VIEW v_comment_list AS
SELECT c.id       AS commentId,
       c.content,
       c.createdAt,
       c.updatedAt,
       c.postId,
       u.filePath AS userFilePath,
       u.id       AS userId,
       u.name     AS userName
FROM comment c
         LEFT JOIN user u ON c.userId = u.id;


CREATE OR REPLACE VIEW v_post_list AS
SELECT p.id       AS postId,
       c.commentCount,
       p.content,
       p.createdAt,
       pl.likeCount,
       p.updatedAt,
       u.filePath AS userFilePath,
       p.userId,
       u.name     AS userName
FROM post p
         LEFT JOIN user u ON p.userId = u.id
         LEFT JOIN (SELECT postId, COUNT(*) AS commentCount FROM comment GROUP BY postId) c ON p.id = c.postId
         LEFT JOIN (SELECT postId, COUNT(*) AS likeCount FROM post_like GROUP BY postId) pl ON p.id = pl.postId;




