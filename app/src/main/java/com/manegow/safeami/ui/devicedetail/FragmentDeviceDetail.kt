package com.manegow.safeami.ui.devicedetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.manegow.safeami.R


class FragmentDeviceDetail : Fragment() {

    companion object {
        fun newInstance() =
            FragmentDeviceDetail()
    }

    private lateinit var viewModel: FragmentDeviceDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_device_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FragmentDeviceDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
