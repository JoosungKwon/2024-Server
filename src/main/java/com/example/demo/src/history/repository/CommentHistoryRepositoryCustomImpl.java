package com.example.demo.src.history.repository;

import com.example.demo.src.admin.model.request.AdminHistorySearchReq;
import com.example.demo.src.history.entity.CommentHistory;
import com.example.demo.src.post.entity.QPost;
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

import static com.example.demo.src.history.entity.QCommentHistory.commentHistory;
import static com.example.demo.src.post.entity.QPost.post;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CommentHistoryRepositoryCustomImpl implements CommentHistoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<CommentHistory> findAllBy(AdminHistorySearchReq adminHistorySearchReq, Pageable pageable) {

        Predicate[] conditionExpression = {
                conditionDomainId(adminHistorySearchReq.getDomainId()),
                conditionDateStart(adminHistorySearchReq.getStart()),
                conditionDateEnd(adminHistorySearchReq.getEnd())
        };

        List<CommentHistory> result = queryFactory.selectFrom(commentHistory)
                .where(conditionExpression)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getSortOrderSpecifier(pageable.getSort()))
                .fetch();

        Long totalCount = queryFactory.select(Wildcard.count)
                .from(commentHistory)
                .where(conditionExpression)
                .fetchOne();

        return new PageImpl<>(result, pageable, totalCount == null ? 0 : totalCount);
    }


    private Predicate conditionDateStart(LocalDateTime start) {
        return commentHistory.createdAt.goe(start);
    }


    private Predicate conditionDateEnd(LocalDateTime end) {
        return commentHistory.createdAt.loe(end);
    }


    private Predicate conditionDomainId(Long domainId) {
        if (domainId != null) {
            return commentHistory.id.eq(domainId);
        }
        return null;
    }


    private OrderSpecifier[] getSortOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orderSpecifierList = new ArrayList<>();

        if (sort != null) {
            for (Sort.Order order : sort) {
                PathBuilder orderByExpression = new PathBuilder(QPost.class, post.getMetadata());
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
