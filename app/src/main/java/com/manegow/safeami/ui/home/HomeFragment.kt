package com.manegow.safeami.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.manegow.safeami.R
import com.manegow.safeami.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingHome : FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        bindingHome.button.setOnClickListener {v: View ->
            v.findNavController().navigate(HomeFragmentDirections.actionNavHomeToNavMap())
        }
        return bindingHome.root
    }
}

/*
binding.btnAgree.setOnClickListener {v: View ->

            Log.e("Telephone Data", binding.tietPhoneNumber!!.text.toString())
            Log.e("Password Data", binding.tietPassword!!.text.toString())


                    v.findNavController().navigate(UserDataRegisterFragmentDirections.actionUserDataRegisterFragmentToSmsCodeRegisterFragment(binding.tietPhoneNumber.text.toString(),binding.tietPassword.text.toString()))

                userDataRegisterViewModel.buildAlert (context!!,"Error","Debes ingresar el número de télefono","ok")
            }
 */