package com.spring.module.auth.domain.model

import com.spring.module.auth.domain.common.BasicEntity
import com.spring.module.auth.domain.model.enum.GenderEnum
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate

@Entity
@Table(name = "`user`", catalog = "user_mng")
@DynamicUpdate
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null,
    @Column(nullable = false, unique = true)
    var username:String,
    @Column(nullable = false)
    var password:String,
    @Column(nullable = false)
    var name:String,
    @Column(nullable = false, unique = true)
    var rrn:String,
    @Column(nullable = false)
    var birthYear: Int? = null,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var gender: GenderEnum? = null,
    @Column(nullable = false)
    var phoneNumber:String,
    @Column(nullable = false)
    var address:String
) : BasicEntity()