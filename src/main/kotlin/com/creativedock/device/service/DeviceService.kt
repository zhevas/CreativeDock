package com.creativedock.device.service

import com.creativedock.device.dao.DeviceRepository
import com.creativedock.device.dao.entity.DeviceEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.ZonedDateTime
import java.util.UUID

@Service
class DeviceService(private val repository: DeviceRepository) {

    fun addDevice(device: DeviceEntity):DeviceEntity {
        return repository.save(device)
    }

    fun removeDevice(deviceId: String, userId: String) {
        val uuidId = UUID.fromString(deviceId)
        if (!repository.existsByIdAndUserId(uuidId, userId)) {
            throw notFoundException(deviceId, userId)
        }
        repository.deleteById(uuidId)
    }

    fun updateDevice(deviceId: String, device: DeviceEntity):DeviceEntity {
        val oldEntity = repository.findByIdAndUserId(UUID.fromString(deviceId), device.userId)
            .orElseThrow { notFoundException(deviceId, device.userId) }
        oldEntity.apply {
            operatingSystem = device.operatingSystem
            updated = ZonedDateTime.now()
        }
        return repository.save(oldEntity)
    }

    fun getUserDevices(userId: String): List<DeviceEntity> {
        return repository.findAllByUserId(userId)
    }

    fun getAllDevices(page: Int, size: Int): Page<DeviceEntity> {
        val pageable = PageRequest.of(page, size)
        return repository.findAll(pageable)
    }

    fun getUSerDeviceById(deviceId: String, userId: String):DeviceEntity {
        return repository.findByIdAndUserId(UUID.fromString(deviceId), userId)
            .orElseThrow { notFoundException(deviceId, userId) }
    }

    private fun notFoundException(deviceId: String, userId: String) =
        ResponseStatusException(HttpStatus.NOT_FOUND, "Device with id - $deviceId not found for user - $userId")
}
