package com.vitaz.devicetracker.ui.main

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vitaz.devicetracker.databinding.DevicesItemRowHolderBinding
import com.vitaz.devicetracker.misc.getFrescoProgressBarLoadable
import com.vitaz.devicetracker.misc.setUri
import com.vitaz.devicetracker.networking.dto.Device

class DevicesRecyclerAdapter(
    val context: Context
): RecyclerView.Adapter<DevicesRecyclerAdapter.DeviceItemHolder>() {
    private var listener: DevicesRecyclerAdapter.OnDeviceSelectListener? = null
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var deviceList: MutableList<Device> = mutableListOf()

    internal fun setDevices(deviceList: List<Device>?) {
        this.deviceList.removeAll(this.deviceList)
        if (deviceList != null) {
            this.deviceList.addAll(deviceList)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DeviceItemHolder(DevicesItemRowHolderBinding.inflate(inflater, parent, false))


    override fun onBindViewHolder(holder: DevicesRecyclerAdapter.DeviceItemHolder, position: Int) =
        holder.bindDevices(deviceList[position])

    override fun getItemCount() = deviceList.size

    inner class DeviceItemHolder(
        private val view: DevicesItemRowHolderBinding
    ) : RecyclerView.ViewHolder(view.root), View.OnClickListener {

        private lateinit var device: Device

        init {
            view.root.setOnClickListener(this)
        }

        fun bindDevices(device: Device) {
            view.device = device
            this.device = device

            val uri = Uri.parse(device.imageUrl)
            view.deviceImage.hierarchy.setProgressBarImage(getFrescoProgressBarLoadable())
            setUri(view.deviceImage, uri, true)

            // This will reload image if Fresco faced some problems with initial loading
            view.deviceImage.setOnClickListener {
                setUri(view.deviceImage, uri, true);
            }
        }

        override fun onClick(p0: View?) {
            listener?.onDeviceSelect(this.device)
        }
    }

    interface OnDeviceSelectListener {
        fun onDeviceSelect(device: Device)
    }

    fun setDeviceSelectListener(listener: OnDeviceSelectListener) {
        this.listener = listener
    }
}
