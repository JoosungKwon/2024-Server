package com.example.demo.common;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

public class Constant {

    public enum SocialLoginType {
        GOOGLE,
        KAKAO,
        NAVER
    }

    public static final class Value {
        public static final String FALSE_VALUE = "0";
        public static final String ZERO_VALUE = "0";
        public static final int NONE = 0;
        public static final int YES = 1;
        public static final int NO = 0;
        public static final int TRUE = 1;
        public static final int FALSE = 0;
        public static final int ZERO = 0;
        public static final long ZERO_LONG = 0L;
        public static final int DEFAULT_COUNT = 1;
        public static final int NOT_EXIST = 0;
        public static final int EXIST = 1;
        public static final String EMPTY = "";
    }

    @NoArgsConstructor(access = PRIVATE)
    public static final class Char {
        public static final String SEMI_COLON = ":";
        public static final String SLASH = "/";
        public static final String SPACE = " ";
        public static final String HYPHEN = "-";
        public static final String EQUAL = "=";
        public static final String COMMA = ",";
        public static final String DOT = ".";
        public static final String VERSION = "v";
        public static final String ZIP = "zip";
        public static final String UNDER_BAR = "_";
        public static final String PIPE = "|";
        public static final String AT = "@";
        public static final String ASTERISK = "*";
    }


    @NoArgsConstructor(access = PRIVATE)
    public static final class File {
        public static final int MAX_UPLOAD_FILE_COUNT = 10;
    }

    @NoArgsConstructor(access = PRIVATE)
    public static final class FilePath {
        public static final String PROFILE_IMAGE = "/profileImage";
    }

}



