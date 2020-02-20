package com.manegow.safeami.ui.splashscreen

import androidx.lifecycle.ViewModel

class SplashscreenViewModel : ViewModel() {
    // TODO: Implement the ViewModel
}



/*
package com.manegow.coin

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class splashscreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragmentz
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        return inflater.inflate(R.layout.fragment_splashscreen, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed({
            context?.let {
                if(true){
                    findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                }
                else{
                    findNavController().navigate(R.id.action_splashscreen_to_paymentFragment)
                }

            }
        }, 2500)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }
}

 */