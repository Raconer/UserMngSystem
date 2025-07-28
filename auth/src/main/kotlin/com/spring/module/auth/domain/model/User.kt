package com.spring.module.auth.domain.model

import com.spring.module.auth.domain.common.BasicEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "`user`")
data class User(
    @Id
    val id:Long? = null,
    @Column(nullable = false)
    var name:String,
) : BasicEntity()