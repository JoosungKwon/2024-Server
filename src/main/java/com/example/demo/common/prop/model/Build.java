package com.example.demo.common.prop.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Build {

    private final String name;
    private final String version;
    private final String region;
    private final String environment;

}