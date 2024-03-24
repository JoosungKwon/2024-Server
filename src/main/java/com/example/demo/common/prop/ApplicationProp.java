package com.example.demo.common.prop;

import com.example.demo.common.prop.model.Build;
import com.example.demo.common.prop.model.Storage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "application")
public class ApplicationProp {

    private final Build build;
    private final Storage storage;

}