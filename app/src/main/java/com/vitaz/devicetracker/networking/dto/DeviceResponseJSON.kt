package com.vitaz.devicetracker.networking.dto

data class DeviceResponseJSON (
    val devices: List<Device>
)

data class Device(
    val id: String,
    val title: String,
    val description: String,
    val type: String,
    val price: String,
    val currency: String,
    val rating: Float,
    val isFavourite: Boolean,
    val imageUrl: String
)