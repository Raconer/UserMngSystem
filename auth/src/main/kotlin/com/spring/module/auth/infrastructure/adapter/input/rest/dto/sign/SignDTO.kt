package com.spring.module.auth.infrastructure.adapter.input.rest.dto.sign

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/**
 * Spring Security에서 인증 정보 전달용 DTO.
 * JWT 필터에서 인증 객체로 사용되며, 사용자명(username)과 비밀번호(password)만 유지한다.
 */
data class SignDTO (
    private var username: String,
    private var password: String? = null
):UserDetails{
    /**
     * 현재는 권한을 사용하지 않기 때문에 null 반환.
     * 필요한 경우 ROLE 정보를 여기서 반환해야 함.
     */
    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return null
    }
    /**
     * 패스워드 설정용 커스텀 메서드.
     */
    fun setPassword(password: String?){
        this.password = password
    }

    /**
     * 비밀번호 반환 (Spring Security 내부 사용)
     */
    override fun getPassword(): String? {
        return this.password
    }
    /**
     * 사용자명 반환 (Spring Security 내부 사용)
     */
    override fun getUsername(): String {
        return this.username
    }
    /**
     * 계정 만료 여부. false이면 만료된 계정으로 판단.
     */
    override fun isAccountNonExpired(): Boolean {
        return false
    }
    /**
     * 계정 잠김 여부. false이면 잠긴 계정으로 판단.
     */
    override fun isAccountNonLocked(): Boolean {
        return false
    }
    /**
     * 자격 증명(패스워드) 만료 여부.
     */
    override fun isCredentialsNonExpired(): Boolean {
        return false
    }
    /**
     * 계정 활성화 여부.
     */
    override fun isEnabled(): Boolean {
        return false
    }
}