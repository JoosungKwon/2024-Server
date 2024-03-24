package com.example.demo.src.report.repository;


import com.example.demo.src.report.entity.DomainType;
import com.example.demo.src.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.example.demo.common.entity.BaseEntity.State;

public interface ReportRepository extends ReportRepositoryCustom, JpaRepository<Report, Long> {

    List<Report> findByDomainIdAndDomainTypeAndState(Long domainId, DomainType domainType, State state);

    @Query("SELECT r FROM Report r WHERE r.domainType = :type AND r.state = 'ACTIVE' AND r.id IN :ids")
    List<Report> findByDomainTypeAndIdIn(@Param("type") DomainType type, @Param("ids") List<Long> ids);
}
