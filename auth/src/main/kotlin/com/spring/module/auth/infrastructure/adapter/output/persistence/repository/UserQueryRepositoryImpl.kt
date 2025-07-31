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

/**
 * 사용자 커스텀 쿼리 구현체
 * - QueryDSL 사용
 */
class UserQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : UserQueryRepository {

    /**
     * 페이징 처리된 사용자 목록 조회
     * @param pageable 페이지 및 사이즈 정보
     * @return 사용자 페이지 결과
     */
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

    /**
     * username 기준 로그인 정보 조회 (username, password)
     * @param username 사용자 ID
     * @return SignDTO 또는 null
     */
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

    /**
     * 연령대별 사용자 목록 조회
     * @param ageGroup 예: 20, 30, 40 (연령대)
     * @return 연령대에 해당하는 사용자 리스트
     */
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