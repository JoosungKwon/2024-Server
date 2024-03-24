package com.example.demo.common.prop.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class UserFileBucket {

    private final String region;
    private final String bucketName;
    private final Long uploadUrlExpirationSeconds;
    private final Long downloadUrlExpirationSeconds;

}
