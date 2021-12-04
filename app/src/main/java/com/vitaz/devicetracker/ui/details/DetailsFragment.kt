package com.vitaz.devicetracker.ui.details

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.vitaz.devicetracker.R

import com.vitaz.devicetracker.databinding.DetailsFragmentBinding
import com.vitaz.devicetracker.misc.setUri
import com.vitaz.devicetracker.ui.MainViewModel


class DetailsFragment: Fragment() {

    private val viewModel: MainViewModel by viewModels(
        ownerProducer = { requireActivity() }
    )

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        bindObservers()
        bindListeners()

        return view
    }

    private fun bindListeners() {
        binding.favourite.setOnClickListener {
            viewModel.selectedDevice.value?.let {
                viewModel.changeFavouriteStatus(it)
                setFavourite()
            }
        }
    }

    private fun bindObservers() {
        viewModel.selectedDevice.observe(viewLifecycleOwner, Observer { device ->
            if (device != null) {
                val uri = Uri.parse(device.imageUrl)
                setUri(binding.deviceImage, uri, true)
                binding.device = device
                setFavourite()
            }
        })
    }

    private fun setFavourite() {
        when (viewModel.selectedDevice.value!!.isFavourite) {
            true -> binding.favourite.setColorFilter(ContextCompat.getColor(requireContext(), R.color.accent), android.graphics.PorterDuff.Mode.SRC_IN)
            false -> binding.favourite.setColorFilter(ContextCompat.getColor(requireContext(), R.color.secondaryText), android.graphics.PorterDuff.Mode.SRC_IN)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
