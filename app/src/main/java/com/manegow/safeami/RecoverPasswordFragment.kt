package com.manegow.safeami

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class RecoverPasswordFragment : Fragment() {

    companion object {
        fun newInstance() = RecoverPasswordFragment()
    }

    private lateinit var viewModel: RecoverPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recoverpassword, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RecoverPasswordViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
