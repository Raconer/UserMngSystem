package com.spring.module.auth.infrastructure.adapter.output.persistence.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.spring.module.auth.domain.model.QUser.user
import com.spring.module.auth.domain.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

class UserQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : UserQueryRepository {
    override fun searchUsers(pageable: Pageable): Page<User> {

        val query = this.queryFactory
            .selectFrom(user)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())

        val results = query.fetch()
        val total = queryFactory
            .select(user.count())
        .from(user)
            .fetchOne() ?: 0L

        return PageImpl(results, pageable, total)
    }
}