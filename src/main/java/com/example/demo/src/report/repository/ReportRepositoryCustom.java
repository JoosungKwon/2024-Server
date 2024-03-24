package com.example.demo.src.report.repository;

import com.example.demo.src.admin.model.request.adminReportSearchReq;
import com.example.demo.src.report.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportRepositoryCustom {

    Page<Report> findAllBy(adminReportSearchReq adminReportSearchReq, Pageable page);
}
