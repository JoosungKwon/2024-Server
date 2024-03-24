package com.example.demo.src.system.model.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@Schema(description = "시스템 정보")
public class SystemInfoRes {

    @Schema(description = "서비스 이름", example = "Demo Service")
    private final String name;

    @Schema(description = "서버 버전", example = "1.0.0")
    private final String version;

    @Schema(description = "서버 리전", example = "us-east-1")
    private final String region;

    @Schema(description = "환경", example = "local")
    private final String environment;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Seoul")
    @Schema(description = "서버 현재 시간", example = "2024-03-01 00:00:00")
    private final LocalDateTime timestamp;

}


