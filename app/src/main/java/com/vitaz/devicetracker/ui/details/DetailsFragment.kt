package com.vitaz.devicetracker.ui.details

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer

import com.vitaz.devicetracker.databinding.DetailsFragmentBinding
import com.vitaz.devicetracker.misc.setUri
import com.vitaz.devicetracker.ui.main.MainViewModel


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

        return view
    }

    private fun bindObservers() {
        viewModel.selectedDevice.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val uri = Uri.parse(it.imageUrl)
                setUri(binding.deviceImage, uri, true)
                binding.device = it
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
