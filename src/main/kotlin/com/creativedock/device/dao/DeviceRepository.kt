package com.creativedock.device.dao

import com.creativedock.device.dao.entity.DeviceEntity
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.Optional
import java.util.UUID

interface DeviceRepository : PagingAndSortingRepository<DeviceEntity, UUID> {

    fun existsByIdAndUserId(id: UUID, userId: String): Boolean

    fun findByIdAndUserId(id: UUID, userId: String): Optional<DeviceEntity>

    fun findAllByUserId(userId: String): List<DeviceEntity>
}
