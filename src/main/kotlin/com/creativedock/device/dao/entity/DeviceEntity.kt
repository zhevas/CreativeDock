package com.creativedock.device.dao.entity

import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class DeviceEntity(
    @Id
    var id: UUID = UUID.randomUUID(),
    var userId: String,
    var operatingSystem: String,
    var created: ZonedDateTime? = ZonedDateTime.now(),
    var updated: ZonedDateTime = ZonedDateTime.now()
)
