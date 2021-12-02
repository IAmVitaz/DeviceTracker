package com.vitaz.devicetracker.networking.dto

data class DeviceResponseJSON (
    val devices: List<Device>
)

data class Device(
    val id: String,
    val title: String,
    val description: String,
    val type: String,
    val price: Int,
    val currency: String,
    val rating: Double,
    val isFavourite: Boolean,
    val imageUrl: String
)