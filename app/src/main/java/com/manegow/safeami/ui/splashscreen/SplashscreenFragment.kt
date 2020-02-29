package com.manegow.safeami.ui.splashscreen

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.manegow.safeami.R
import com.manegow.safeami.database.SafeAmiDatabase
import com.manegow.safeami.databinding.FragmentSplashscreenBinding


class SplashscreenFragment : Fragment() {

    private lateinit var splashscreenViewModel: SplashscreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingSplashScreen: FragmentSplashscreenBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_splashscreen, container, false)

        bindingSplashScreen.setLifecycleOwner(this)

        val application = requireNotNull(this.activity).application
        val dataSource = SafeAmiDatabase.getInstance(application).safeAmiDatabaseDao()

        val viewModelFactory = SplashscreenViewModelFactory(dataSource, application)
        splashscreenViewModel =
            ViewModelProviders.of(this, viewModelFactory)
                .get(SplashscreenViewModel::class.java)

        bindingSplashScreen.splashScreenViewModel = splashscreenViewModel

        splashscreenViewModel.navigateToMainScreen.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                println("Current Destination ${this.findNavController().currentDestination}")
                this.findNavController().navigate(
                    SplashscreenFragmentDirections
                        .actionSplashscreenFragmentToNavHome())
                splashscreenViewModel.onMainScreenNavigated()
            }
        })

        splashscreenViewModel.navigateToLogin.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                this.findNavController().navigate(
                    SplashscreenFragmentDirections
                        .actionSplashscreenFragmentToNavLogin())
                splashscreenViewModel.onLoginNavigated()
            }
        })

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        return bindingSplashScreen.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed({
            context?.let {
                splashscreenViewModel.getLocalUser()
            }
        }, 2500)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }
}
