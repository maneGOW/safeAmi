package com.manegow.safeami.ui.devices

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.manegow.safeami.R


class FragmentDevices : Fragment() {

    companion object {
        fun newInstance() = FragmentDevices()
    }

    private lateinit var viewModel: FragmentDevicesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_devices, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FragmentDevicesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
