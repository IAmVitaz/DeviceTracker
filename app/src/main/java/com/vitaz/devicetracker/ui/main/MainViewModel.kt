package com.vitaz.devicetracker.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vitaz.devicetracker.networking.clients.DeviceService
import com.vitaz.devicetracker.networking.dto.Device
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException

class MainViewModel : ViewModel() {
    private val deviceService = DeviceService.getDevices()
    var deviceList = MutableLiveData<List<Device>>()


    fun getDevices() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentOperation: String = "Fetching List of Devices"
            try {
                val response = deviceService.getFullListOfDevices()
                val newList: List<Device> = response.devices
                deviceList.postValue(newList)
            } catch (e: HttpException) {
                handleHttpException(currentOperation, e)
            } catch (e: Exception) {
                Log.i(currentOperation, "An Error Occurred: ${e.message}")
            }
        }
    }

    private fun handleHttpException(tag: String, e: HttpException) {
        if (e.code() != 500) {
            try {
                val message = e.response()?.errorBody()
                if (message != null) {
                    val messageJSON = JSONObject(message.string())
                    Log.i(tag, "HttpException: $messageJSON")
                }
            } catch (e: JSONException) {
                Log.i(tag, "JSONException: $e")
            }
        }
    }


}