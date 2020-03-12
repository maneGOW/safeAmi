package com.manegow.safeami.ui.adddevice

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.manegow.safeami.R


class FragmentAddDevice : Fragment() {

    companion object {
        fun newInstance() = FragmentAddDevice()
    }

    private lateinit var viewModel: FragmentAddDeviceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_device, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FragmentAddDeviceViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
