package com.example.demo.src.report.repository;

import com.example.demo.src.admin.model.request.adminReportSearchReq;
import com.example.demo.src.report.entity.DomainType;
import com.example.demo.src.report.entity.QReport;
import com.example.demo.src.report.entity.Report;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.src.report.entity.QReport.report;


@Slf4j
@Repository
@RequiredArgsConstructor
public class ReportRepositoryCustomImpl implements ReportRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<Report> findAllBy(adminReportSearchReq adminReportSearchReq, Pageable pageable) {


        Predicate[] conditionExpression = {
                conditionDomain(adminReportSearchReq.getDomainId(), adminReportSearchReq.getDomainType()),
                conditionDateStart(adminReportSearchReq.getStart()),
                conditionDateEnd(adminReportSearchReq.getEnd())
        };

        List<Report> result = queryFactory.selectFrom(report)
                .where(conditionExpression)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getSortOrderSpecifier(pageable.getSort()))
                .fetch();

        Long totalCount = queryFactory.select(Wildcard.count)
                .from(report)
                .where(conditionExpression)
                .fetchOne();

        return new PageImpl<>(result, pageable, totalCount == null ? 0 : totalCount);
    }


    private Predicate conditionDateStart(LocalDateTime start) {
        return report.createdAt.goe(start);
    }


    private Predicate conditionDateEnd(LocalDateTime end) {
        return report.createdAt.loe(end);
    }


    private Predicate conditionDomain(Long domainId, DomainType domainType) {
        if (domainId != null && domainType != null) {
            return report.domainId.eq(domainId)
                    .and(report.domainType.eq(domainType));
        }
        return null;
    }


    private OrderSpecifier[] getSortOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orderSpecifierList = new ArrayList<>();

        if (sort != null) {
            for (Sort.Order order : sort) {
                PathBuilder orderByExpression = new PathBuilder(QReport.class, report.getMetadata());
                OrderSpecifier orderSpecifier = new OrderSpecifier(
                        order.isAscending() ? Order.ASC : Order.DESC,
                        orderByExpression.get(order.getProperty())
                );
                orderSpecifierList.add(orderSpecifier);
            }
        }

        return orderSpecifierList.toArray(OrderSpecifier[]::new);
    }
}
