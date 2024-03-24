package com.example.demo.src.admin.service;

import com.example.demo.src.admin.model.request.AdminBlockReq;
import com.example.demo.src.admin.model.request.adminReportSearchReq;
import com.example.demo.src.comment.repository.CommentRepository;
import com.example.demo.src.post.repository.PostRepository;
import com.example.demo.src.report.entity.Report;
import com.example.demo.src.report.model.response.ReportRes;
import com.example.demo.src.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminReportService {

    private final ReportRepository reportRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;


    public Page<ReportRes> getReportsBy(adminReportSearchReq request, Pageable pageable) {
        Page<Report> reports = reportRepository.findAllBy(request, pageable);

        return reports.map(ReportRes::from);
    }


    @Transactional
    public ReportRes changeStateToBlock(AdminBlockReq adminBlockReq) {
        if (adminBlockReq.getDomainType().isPost()) {
            postRepository.findById(adminBlockReq.getDomainId())
                    .ifPresent(post -> {
                        post.block();
                        postRepository.save(post);
                    });
        } else if (adminBlockReq.getDomainType().isComment()) {
            commentRepository.findById(adminBlockReq.getDomainId())
                    .ifPresent(comment -> {
                        comment.block();
                        commentRepository.save(comment);
                    });
        }
        return null;
    }


    @Transactional
    public void deleteReport(Long reportId) {
        reportRepository.deleteById(reportId);
    }

}
