package com.vitaz.devicetracker.networking.clients

import com.vitaz.devicetracker.networking.dto.DeviceResponseJSON
import retrofit2.http.GET

interface DeviceClient {
    @GET("IAmVitaz/DeviceTracker/main/devices.json")
    suspend fun getFullListOfDevices (): DeviceResponseJSON
}
