package com.vitaz.devicetracker.networking.clients

import com.vitaz.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DeviceService {
    fun getDevices(): DeviceClient {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DeviceClient::class.java)
    }
}
