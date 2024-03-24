package com.example.demo.src.report.service;

import com.example.demo.common.exceptions.NotFoundException;
import com.example.demo.common.exceptions.ServicePolicyException;
import com.example.demo.common.model.response.BaseResponseStatus;
import com.example.demo.src.comment.repository.CommentRepository;
import com.example.demo.src.post.repository.PostRepository;
import com.example.demo.src.report.entity.DomainType;
import com.example.demo.src.report.entity.Report;
import com.example.demo.src.report.model.request.ReportCreateReq;
import com.example.demo.src.report.model.response.ReportRes;
import com.example.demo.src.report.repository.ReportRepository;
import com.example.demo.src.user.entity.User;
import com.example.demo.src.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public ReportRes createReport(ReportCreateReq request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException(BaseResponseStatus.USER_NOT_FOUND));

        Report report = Report.builder()
                .reportType(request.getReportType())
                .domainId(request.getDomainId())
                .domainType(request.getDomainType())
                .userId(request.getUserId())
                .build();

        verifySelfReport(request.getDomainId(), request.getDomainType(), user.getId());

        Report saveReport = reportRepository.save(report);

        return ReportRes.from(saveReport);
    }

    private void verifySelfReport(Long domainId, DomainType domainType, Long userId) {
        if (DomainType.COMMENT.equals(domainType)) {
            commentRepository.findById(domainId)
                    .ifPresent(report -> {
                        if (report.getUserId().equals(userId)) {
                            throw new ServicePolicyException(BaseResponseStatus.SELF_REPORT);
                        }
                    });
        } else if (DomainType.POST.equals(domainType)) {
            postRepository.findById(domainId)
                    .ifPresent(report -> {
                        if (report.getUserId().equals(userId)) {
                            throw new ServicePolicyException(BaseResponseStatus.SELF_REPORT);
                        }
                    });
        }
    }

}
