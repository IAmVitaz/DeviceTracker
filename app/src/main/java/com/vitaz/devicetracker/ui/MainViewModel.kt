package com.vitaz.devicetracker.ui

import android.util.Log
import androidx.lifecycle.*
import com.vitaz.devicetracker.networking.clients.DeviceService
import com.vitaz.devicetracker.networking.dto.Device
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.util.*

class MainViewModel : ViewModel() {
    private val deviceService = DeviceService.getDevices()
    private var fullDeviceList = MutableLiveData<List<Device>>()
    var filteredDeviceList = MediatorLiveData<List<Device>>()
    var selectedDevice = MutableLiveData<Device>()
    var searchQuery = MutableLiveData<String>()

    init {
        searchQuery.value = ""
        filteredDeviceList.addSource(searchQuery) {
            applySearchQuery(it)
        }
        filteredDeviceList.addSource(fullDeviceList) {
            filteredDeviceList.value = it
        }
    }

    fun getDevices() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentOperation: String = "Fetching List of Devices"
            try {
                val response = deviceService.getFullListOfDevices()
                val newList: List<Device> = response.devices
                fullDeviceList.postValue(newList)
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

    fun isDeviceListNull(): Boolean {
        if (fullDeviceList.value.isNullOrEmpty()) return true
        return false
    }

    private fun applySearchQuery(searchQuery: String) {
        fullDeviceList.value?.let {
            val filteredList = it.filter { device ->
                device.title.lowercase(Locale.ROOT).contains(searchQuery.lowercase(Locale.ROOT)) ||
                device.type.lowercase(Locale.ROOT).contains(searchQuery.lowercase(Locale.ROOT))
            }
            filteredDeviceList.value = filteredList
        }
    }

    fun changeFavouriteStatus(device: Device) {
        val currentState = device.isFavourite
        fullDeviceList.value?.let {
            it.find { it == device }?.isFavourite = !currentState
            fullDeviceList.value = it
        }
        //TODO here we changed favourite status locally. Need addition post API call too notify server
    }

    fun getDetail(): LiveData<List<Device>?> {
        return fullDeviceList
    }
}
