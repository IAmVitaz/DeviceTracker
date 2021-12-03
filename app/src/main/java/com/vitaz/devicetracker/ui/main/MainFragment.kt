package com.vitaz.devicetracker.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vitaz.MainApplication
import com.vitaz.devicetracker.databinding.MainFragmentBinding
import com.vitaz.devicetracker.networking.NetworkChecker
import com.vitaz.devicetracker.networking.dto.Device
import com.vitaz.gifaro.connectivity.ConnectivityLiveData
import com.vitaz.gifaro.connectivity.LoadableFragment
import com.vitaz.gifaro.connectivity.LoadingState

class MainFragment : LoadableFragment(), DevicesRecyclerAdapter.OnDeviceSelectListener {

    private lateinit var devicesListRecyclerAdapter: DevicesRecyclerAdapter
    private lateinit var devicesListRecyclerView: RecyclerView

    private val viewModel: MainViewModel by viewModels(
        ownerProducer = { requireActivity() }
    )

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        devicesListRecyclerView = binding.devicesRecyclerView
        connectivityLiveData = ConnectivityLiveData(MainApplication.instance)
        loadingProgressBar = binding.loadingProgressBar
        statusButton = binding.statusButton
        errorMessage = binding.errorMessage

        setupDeviceListRecyclerAdapter()
        bindObservers()

        return view
    }

    private fun setupDeviceListRecyclerAdapter() {
        devicesListRecyclerAdapter = DevicesRecyclerAdapter(requireContext())
        devicesListRecyclerView.adapter = devicesListRecyclerAdapter
        val layout = GridLayoutManager(requireContext(), 1)
        devicesListRecyclerAdapter.setDeviceSelectListener(this)
        devicesListRecyclerView.layoutManager = layout
    }

    private fun bindObservers() {

        connectivityLiveData.observe(viewLifecycleOwner, { isAvailable ->
            when (isAvailable) {
                true -> {
                    viewModel.getDevices()
                    onLoadingStateChanged(LoadingState.LOADING, devicesListRecyclerView)
                }
                false -> onLoadingStateChanged(LoadingState.NO_INTERNET, devicesListRecyclerView)
            }
        })
        if (!NetworkChecker.isNetworkAvailable(requireContext())) {
            onLoadingStateChanged(LoadingState.NO_INTERNET, devicesListRecyclerView)
        }

        viewModel.deviceList.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {
                onLoadingStateChanged(LoadingState.ERROR, devicesListRecyclerView)
            } else {
                devicesListRecyclerAdapter.setDevices(it)
                onLoadingStateChanged(LoadingState.LOADED, devicesListRecyclerView)
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDeviceSelect(device: Device) {
        viewModel.selectedDevice.value = device
        val action = MainFragmentDirections.actionMainFragmentToDetailsFragment()
        findNavController().navigate(action)
    }

}
