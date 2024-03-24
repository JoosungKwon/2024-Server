package com.example.demo.src.admin.model.request;

import com.example.demo.src.report.entity.DomainType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Schema(description = "차단 요청")
public class AdminBlockReq {

    @Schema(description = "차단할 도메인 ID", example = "1")
    private final Long domainId;

    @Schema(description = "차단할 도메인 타입", example = "COMMENT")
    private final DomainType domainType;

    @Builder
    @JsonCreator
    public AdminBlockReq(
            @JsonProperty("domainId") Long domainId,
            @JsonProperty("domainType") DomainType domainType
    ) {
        this.domainId = domainId;
        this.domainType = domainType;
    }
}
