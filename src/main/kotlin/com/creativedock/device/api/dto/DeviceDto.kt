package com.creativedock.device.api.dto

import java.time.ZonedDateTime
import java.util.UUID
import javax.persistence.Id

data class DeviceDto(
    val id: String?,
    val operatingSystem: OperatingSystem,
    val created: ZonedDateTime?,
    val updated: ZonedDateTime?
)

enum class OperatingSystem(val caption: String) {
    ANDROID("Android"),
    IOS("iOS");

    companion object {
        fun getByCaption(caption: String) =
            values().find { it.caption == caption } ?: error("Operating system with caption $caption not found")
    }
}
