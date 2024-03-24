package com.example.demo.common;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Router {

    public static final String API_ALL_VERSION = "/v?";
    public static final String WILD_CARD_PATH = "/**";
    public static final String LATEST_VERSION = "/v1";
    public static final String V1 = "/v1";

    @NoArgsConstructor(access = PRIVATE)
    public static final class Common {
        public static final String LIST = "/list";
        public static final String PAGE = "/page";
    }


    @NoArgsConstructor(access = PRIVATE)
    public static final class Domain {
        public static final String ADMIN = "/admin";
        public static final String USERS = "/users";
        public static final String POSTS = "/posts";
        public static final String COMMENTS = "/comments";
        public static final String REPORT = "/report";
    }


    @NoArgsConstructor(access = PRIVATE)
    public static final class Auth {
        public static final String BIZ_ADMIN_AUTH_PATH = "/oauth/check_token";
        public static final String MEDITLINK_AUTH_PATH = "/v1/oauth/checkToken"; // /users/myInfo

        @NoArgsConstructor(access = PRIVATE)
        public static final class Param {
            public static final String TOKEN = "token";
            public static final String USER_ID = "userId";
            public static final String USER_ID_LOWER = "userid";
        }
    }


    @NoArgsConstructor(access = PRIVATE)
    public static final class System {
        public static final String LIVE_CHECK = "/liveCheck";
    }

    @NoArgsConstructor(access = PRIVATE)
    public static final class Admin {

        public static final String FEEDS = "/feeds";
        public static final String HISTORIES = "/histories";
        public static final String REPORTS = "/reports";
        public static final String USERS = "/users";

        @NoArgsConstructor(access = PRIVATE)
        public static final class Args {
            public static final String POST_ID = "/{postId}";
        }
    }

    @NoArgsConstructor(access = PRIVATE)
    public static final class User {
        public static final String LOGIN = "/login";
        public static final String MY_INFO = "/myInfo";
        public static final String MY_POSTS = "/myPosts";
        public static final String MY_COMMENTS = "/myComments";

        @NoArgsConstructor(access = PRIVATE)
        public static final class Args {
            public static final String USER_ID = "/{userId}";
        }
    }

    @NoArgsConstructor(access = PRIVATE)
    public static final class Post {
        public static final String LIKE = "/like";
        public static final String FILES = "/files";
        public static final String UPLOAD_STATUS = "/uploadStatus";

        @NoArgsConstructor(access = PRIVATE)
        public static final class Args {
            public static final String POST_ID = "/{postId}";
            public static final String FILE_ID = "/{fileId}";
        }
    }

    @NoArgsConstructor(access = PRIVATE)
    public static final class Comment {

        @NoArgsConstructor(access = PRIVATE)
        public static final class Args {
            public static final String COMMENT_ID = "/{commentId}";
            public static final String POST_ID = "/{postId}";
        }
    }

}
