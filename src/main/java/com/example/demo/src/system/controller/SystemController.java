package com.example.demo.src.system.controller;


import com.example.demo.common.Router;
import com.example.demo.common.prop.ApplicationProp;
import com.example.demo.common.prop.model.Build;
import com.example.demo.src.system.model.data.SystemInfoRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static com.example.demo.common.Router.System.LIVE_CHECK;
import static org.springframework.http.HttpStatus.OK;


@Tag(
        name = "시스템 헬스 체크 API",
        description = "서버의 상태를 확인하는 API"
)
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(Router.LATEST_VERSION)
public class SystemController {

    private final ApplicationProp applicationProp;

    @Operation(summary = "서버 상태 확인")
    @GetMapping(LIVE_CHECK)
    @ResponseStatus(OK)
    public SystemInfoRes checkServiceStatus() {
        log.info("System Health Check");

        Build buildInfo = applicationProp.getBuild();
        SystemInfoRes data = SystemInfoRes.builder()
                .name(buildInfo.getName())
                .version(buildInfo.getVersion())
                .region(buildInfo.getRegion())
                .environment(buildInfo.getEnvironment())
                .timestamp(LocalDateTime.now())
                .build();

        log.info("System Health Check wi data: {}", data);

        return data;
    }

}

