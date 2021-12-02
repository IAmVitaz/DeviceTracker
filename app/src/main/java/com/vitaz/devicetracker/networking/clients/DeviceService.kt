package com.vitaz.devicetracker.networking.clients

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DeviceService {
    private val BASE_URL = "https://raw.githubusercontent.com/"

    fun getDevices(): DeviceClient {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DeviceClient::class.java)
    }
}
