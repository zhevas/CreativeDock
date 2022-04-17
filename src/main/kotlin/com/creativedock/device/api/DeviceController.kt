package com.creativedock.device.api

import com.creativedock.device.api.dto.DeviceDto
import com.creativedock.device.config.Roles
import com.creativedock.device.mapping.toDto
import com.creativedock.device.mapping.toEntity
import com.creativedock.device.service.DeviceService
import com.creativedock.device.util.baseUrl
import com.creativedock.device.util.getUserId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.net.URI
import java.security.Principal


@RestController
@RequestMapping("/devices")
class DeviceController(private val service: DeviceService) {

    @PostMapping
    @PreAuthorize("hasAnyAuthority('${Roles.CLIENT}')")
    fun addDevice(@RequestBody request: DeviceDto): ResponseEntity<Any> {
        val newDevice = service.addDevice(request.toEntity(getUserId()))
        return ResponseEntity.created(URI.create("$baseUrl/devices/${newDevice.id}")).build()
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('${Roles.CLIENT}')")
    fun getDevice(@PathVariable id: String): DeviceDto {
        return service.getUSerDeviceById(id, getUserId()).toDto()
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('${Roles.CLIENT}')")
    fun removeDevice(@PathVariable id: String): ResponseEntity<Any> {
        service.removeDevice(id,getUserId(),)
        return ResponseEntity.noContent().build()
    }

    //Can implement patch method also, with another DTO with nullable fields
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('${Roles.CLIENT}')")
    fun updateDevice(@RequestBody request: DeviceDto, @PathVariable id: String):DeviceDto {
        return service.updateDevice(id, request.toEntity(getUserId())).toDto()
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('${Roles.CLIENT}')")
    fun getUserDevices(): List<DeviceDto> {
        return service.getUserDevices(getUserId()).map { it.toDto() }
    }


    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('${Roles.BACKOFFICE}')")
    fun getAllDevices(
        @RequestParam(required = false) page: Int = 0,
        @RequestParam(required = false) size: Int = 10
    ): List<DeviceDto> {
        return service.getAllDevices(page, size).map { it.toDto() }.toList()
    }

}
