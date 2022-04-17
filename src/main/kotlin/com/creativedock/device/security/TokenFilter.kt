package com.creativedock.device.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.filter.GenericFilterBean
import org.springframework.web.server.ResponseStatusException
import java.util.Base64
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class TokenFilter(val objectMapper: ObjectMapper) : GenericFilterBean() {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val authentication = try {
            request.getJwtTokenAuthentication().apply { isAuthenticated = true }
        } catch (ex: ResponseStatusException) {
            response.setStatusAndMessage(ex.status, ex.message)
            return
        } catch (ex: Exception) {
            response.setStatusAndMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.message ?: "Unknown exception occurred")
            return
        }
        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }

    private fun ServletResponse.setStatusAndMessage(status: HttpStatus, message: String) {
        (this as HttpServletResponse).apply {
            this.status = status.value()
            this.writer.write(message)
        }
    }

    private fun ServletRequest.getJwtTokenAuthentication(): JwtAuthentication {
        val authHeader = (this as HttpServletRequest).getHeader(HttpHeaders.AUTHORIZATION)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token $authHeader")
        }
        val chunks = authHeader.substringAfter("Bearer ").split(".")
        val decoder = Base64.getUrlDecoder()
        val jwtToken = try {
            objectMapper.readValue<JwtToken>(decoder.decode(chunks[1]))
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token format $authHeader", ex)
        }
        return JwtAuthentication(jwtToken, authHeader)
    }
}

