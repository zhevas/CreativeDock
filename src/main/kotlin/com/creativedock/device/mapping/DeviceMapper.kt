package com.creativedock.device.mapping

import com.creativedock.device.api.dto.DeviceDto
import com.creativedock.device.api.dto.OperatingSystem
import com.creativedock.device.dao.entity.DeviceEntity


//Can use mapstruct, but it's not so useful for kotlin
fun DeviceDto.toEntity(userId: String) = DeviceEntity(
    userId = userId,
    operatingSystem = operatingSystem.caption,
)

fun DeviceEntity.toDto() = DeviceDto(
    id = id.toString(),
    operatingSystem = OperatingSystem.getByCaption(operatingSystem),
    created = created,
    updated = updated
)
