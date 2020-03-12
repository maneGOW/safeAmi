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
import androidx.navigation.fragment.findNavController
import com.manegow.safeami.R
import com.manegow.safeami.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingHome : FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val application = requireNotNull(this.activity).application
        val viewModelFactory = HomeViewModelFactory(this, application)
        val homeViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(HomeViewModel::class.java)

        bindingHome.lifecycleOwner = this

        bindingHome.viewModel = homeViewModel

        homeViewModel.navigateToConfigurationFragment.observe(viewLifecycleOwner, Observer {
            if(it){
                this.findNavController().navigate(HomeFragmentDirections.actionNavHomeToNavProfile())
                homeViewModel.doneNavigation()
            }
        })

        homeViewModel.navigateToDevicesFragment.observe(viewLifecycleOwner, Observer {
            if(it){
                this.findNavController().navigate(HomeFragmentDirections.actionNavHomeToFragmentDevices())
                homeViewModel.doneNavigation()
            }
        })

        homeViewModel.navigateToFriendsFragment.observe(viewLifecycleOwner, Observer {
            if(it){
                this.findNavController().navigate(HomeFragmentDirections.actionNavHomeToNavFriends())
                homeViewModel.doneNavigation()
            }
        })

        homeViewModel.navigateToMapFragment.observe(viewLifecycleOwner, Observer {
            if(it){
                this.findNavController().navigate(HomeFragmentDirections.actionNavHomeToNavMap())
                homeViewModel.doneNavigation()
            }

        })

        homeViewModel.getSOS.observe(viewLifecycleOwner, Observer {

        })

        return bindingHome.root
    }
}
