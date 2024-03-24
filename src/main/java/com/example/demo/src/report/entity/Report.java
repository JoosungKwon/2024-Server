package com.example.demo.src.report.entity;

import com.example.demo.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "report")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DomainType domainType;

    @NotNull
    private Long domainId;

    @NotNull
    private Long userId;

    @Builder
    public Report(ReportType reportType, DomainType domainType, Long domainId, Long userId) {
        this.reportType = reportType;
        this.domainType = domainType;
        this.domainId = domainId;
        this.userId = userId;
    }

}
