package com.creativedock.device

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class DeviceApplication

fun main(args: Array<String>) {
    runApplication<DeviceApplication>(*args)
}
