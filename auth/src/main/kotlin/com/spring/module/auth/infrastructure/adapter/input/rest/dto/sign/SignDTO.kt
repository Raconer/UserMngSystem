package com.spring.module.auth.infrastructure.adapter.input.rest.dto.sign

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class SignDTO (
    private var username: String,
    private var password: String? = null
):UserDetails{
    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return null
    }

    fun setPassword(password: String?){
        this.password = password
    }
    override fun getPassword(): String? {
        return this.password
    }

    override fun getUsername(): String {
        return this.username
    }

    override fun isAccountNonExpired(): Boolean {
        return false
    }

    override fun isAccountNonLocked(): Boolean {
        return false
    }

    override fun isCredentialsNonExpired(): Boolean {
        return false
    }

    override fun isEnabled(): Boolean {
        return false
    }
}