package com.example.demo.common.config;


import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan(basePackages = {
        "com.example.demo.common.prop",
})
public class PropConfig {

}
