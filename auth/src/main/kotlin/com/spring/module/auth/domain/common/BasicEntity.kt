package com.spring.module.auth.domain.common

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BasicEntity {

    // 생성일시: insert 시 자동 저장됨, 이후 수정 불가
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable = false, updatable = false, name = "created_at")
    private var createdAt: LocalDateTime? = null // 생성일시

    // 수정일시: insert 및 update 시 자동 갱신됨
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    @Column(name = "updated_at")
    private var updatedAt: LocalDateTime? = null // 수정일시

    // 외부 접근용 getter
    fun getCreatedAt(): LocalDateTime? = createdAt
    fun getUpdatedAt(): LocalDateTime? = updatedAt
}