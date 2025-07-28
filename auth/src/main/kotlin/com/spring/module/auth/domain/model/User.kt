package com.spring.module.auth.domain.model

import com.spring.module.auth.domain.common.BasicEntity
import jakarta.persistence.*

@Entity
@Table(name = "`user`", catalog = "user_mng")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null,
    @Column(nullable = false, unique = true)
    val username:String,
    @Column(nullable = false)
    val password:String,
    @Column(nullable = false)
    val name:String,
    @Column(nullable = false, unique = true)
    val rrn:String,
    @Column(nullable = false)
    val phoneNumber:String,
    @Column(nullable = false)
    val address:String
) : BasicEntity()