package com.example.demo.common.prop.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class Storage {

    private final String provider;
    private final String environment;
    private final PostFileBucket postFileBucket;
    private final UserFileBucket userFileBucket;

}
