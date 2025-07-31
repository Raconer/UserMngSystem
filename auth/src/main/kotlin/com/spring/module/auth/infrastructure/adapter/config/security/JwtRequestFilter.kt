package com.spring.module.auth.infrastructure.adapter.config.security

import com.spring.module.auth.infrastructure.adapter.input.rest.dto.sign.SignDTO
import com.spring.module.auth.infrastructure.adapter.input.rest.exception.UserNotFoundException
import com.module.prj.core.common.GlobalConstants
import com.module.prj.core.common.ResponseMessages
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtRequestFilter(
    private val jwtUtil: JwtUtil,
) : OncePerRequestFilter() {

    private val EXCLUDE_URL = arrayListOf("/api/user", "/api/sign/in")

    private val EXCLUDE_MAP: Map<String, Set<HttpMethod>> = mapOf(
        "/api/user" to setOf(HttpMethod.POST),
        "/api/sign/in" to setOf(HttpMethod.POST) // 여러 메서드 허용 가능
    )

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val bearerToken =  requireNotNull(request.getHeader(GlobalConstants.AUTHORIZATION_HEADER)) {
            throw UserNotFoundException(ResponseMessages.TOKEN_IS_NULL)
        }

        if (bearerToken.isNotEmpty() && bearerToken.startsWith(GlobalConstants.TOKEN_PREFIX)) {
            val token: String = bearerToken.substring(GlobalConstants.SUB_LEN)

            val claims = this.jwtUtil.getData(token)
            val username: String = claims["username"] as String
            val signDTO = SignDTO(username = username)
            val authenticationToken = UsernamePasswordAuthenticationToken(signDTO, null, signDTO.authorities)
            SecurityContextHolder.getContext().authentication = authenticationToken
        }

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val uri = request.requestURI
        val method = try {
            HttpMethod.valueOf(request.method.uppercase())
        } catch (e: IllegalArgumentException) {
            return false
        }

        return EXCLUDE_MAP[uri]?.contains(method) == true
    }
}