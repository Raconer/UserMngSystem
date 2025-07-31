package com.spring.module.auth.infrastructure.adapter.config.security

import com.spring.module.auth.infrastructure.adapter.input.rest.exception.CustomAuthenticationException
import com.module.prj.core.common.ResponseMessages
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Component
import java.security.SignatureException
import java.util.*

@Component
class JwtUtil {

    // JWT 서명에 사용할 비밀키 (application.yml에서 주입)
    @Value("\${common.jwt.secret_key}")
    private val secretKey: String? = null

    // 토큰 만료 시간(ms 단위, application.yml에서 주입)
    @Value("\${common.jwt.expire_time}")
    private val expired: Long = 0

    /**
     * JWT 토큰 생성
     * @param username 사용자 ID
     * @return 생성된 JWT 문자열
     */
    fun create(username: String): String {
        val header: MutableMap<String, Any> = HashMap()
        val payloads: MutableMap<String, Any> = HashMap()
        header["type"] = "jwt"
        payloads["username"] = username
        val curDate = Date()
        val expiredDate = Date(curDate.time + expired)
        return Jwts.builder()
            .setHeader(header)
            .setClaims(payloads)
            .setIssuedAt(curDate)
            .setExpiration(expiredDate)
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    /**
     * JWT 토큰 파싱하여 Claims 추출
     * @param token JWT 문자열
     * @return Claims 객체
     */
    fun getData(token: String?): Claims {
        isValidate(token)
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).body
    }

    /**
     * JWT 토큰 유효성 검사
     * @param token JWT 문자열
     * @return 유효하면 true, 아니면 예외 발생
     */
    fun isValidate(token: String?): Boolean? {
        if (token == null) throw CustomAuthenticationException(ResponseMessages.TOKEN_IS_NULL)
        return try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
            true
        } catch (ex: AuthenticationException) {
            throw CustomAuthenticationException(ResponseMessages.AUTHENTICATION_FAILED, ex)
        } catch (ex: SignatureException) {
            throw CustomAuthenticationException(ResponseMessages.INVALID_SIGNATURE, ex)
        } catch (ex: MalformedJwtException) {
            throw CustomAuthenticationException(ResponseMessages.MALFORMED_TOKEN, ex)
        } catch (ex: ExpiredJwtException) {
            throw CustomAuthenticationException(ResponseMessages.EXPIRED_TOKEN, ex)
        } catch (ex: UnsupportedJwtException) {
            throw CustomAuthenticationException(ResponseMessages.UNSUPPORTED_TOKEN, ex)
        } catch (ex: IllegalArgumentException) {
            throw CustomAuthenticationException(ResponseMessages.EMPTY_CLAIMS, ex)
        }
    }
    /**
     * HMAC 서명용 SecretKey 반환
     */
    private fun getSigningKey() = Keys.hmacShaKeyFor(secretKey!!.toByteArray())

}