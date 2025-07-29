package com.spring.module.auth.infrastructure.adapter.output.persistence.repository

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import com.spring.module.auth.domain.model.QUser.user
import com.spring.module.auth.domain.model.User
import com.spring.module.auth.infrastructure.adapter.input.rest.dto.sign.SignDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.time.Year

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

    override fun findSignInfoByUsername(username: String): SignDTO? {
        return this.queryFactory.select(
            Projections.constructor(
                SignDTO::class.java,
                user.username,
                user.password
            )
        ).from(user)
            .where(user.username.eq(username))
            .fetchOne()
    }

    override fun findByAgeGroup(ageGroup: Int): List<User> {
        val currentYear = Year.now().value
        val minYear = currentYear - (ageGroup + 9)
        val maxYear = currentYear - ageGroup

        return this.queryFactory.selectFrom(user)
            .where(
                user.birthYear.between(minYear, maxYear)
            ).fetch()

    }

}