package com.creativedock.device.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority

data class JwtToken(val sub: String, val role: String)

class JwtAuthentication(
    private val jwtToken: JwtToken,
    private val stringToken: String,
) : AbstractAuthenticationToken(listOf(SimpleGrantedAuthority(jwtToken.role))) {
    override fun getCredentials(): Any {
        return stringToken
    }

    override fun getPrincipal(): Any {
        return jwtToken.sub
    }
}
