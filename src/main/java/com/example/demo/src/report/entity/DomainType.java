package com.example.demo.src.report.entity;

public enum DomainType {
    POST, COMMENT, ETC;

    public boolean isPost() {
        return this.equals(POST);
    }

    public boolean isComment() {
        return this.equals(COMMENT);
    }

}
