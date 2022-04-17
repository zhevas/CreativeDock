package com.creativedock.device.util

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.servlet.support.ServletUriComponentsBuilder


fun getUserId() = SecurityContextHolder.getContext().authentication.principal as String

val baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()
