package com.manegow.safeami.ui.home

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.manegow.safeami.BaseViewModel

class HomeViewModel(fragment: Fragment, application: Application) : BaseViewModel(fragment, application) {

    private val _navigateToFriendsFragment = MutableLiveData<Boolean>()
    val navigateToFriendsFragment : LiveData<Boolean>
    get() = _navigateToFriendsFragment

    private val _navigateToMapFragment = MutableLiveData<Boolean>()
    val navigateToMapFragment : LiveData<Boolean>
    get() = _navigateToMapFragment

    private val _navigateToDevicesFragment = MutableLiveData<Boolean>()
    val navigateToDevicesFragment : LiveData<Boolean>
    get() = _navigateToDevicesFragment

    private val _navigateToConfigurationFragment = MutableLiveData<Boolean>()
    val navigateToConfigurationFragment : LiveData<Boolean>
    get() = _navigateToConfigurationFragment

    private val _getSOS = MutableLiveData<Boolean>()
    val getSOS : LiveData<Boolean>
    get() = _getSOS

    fun doneNavigation(){
        _navigateToConfigurationFragment.value = false
        _navigateToDevicesFragment.value = false
        _navigateToMapFragment.value = false
        _navigateToFriendsFragment.value = false
    }

    fun navigateToFriendsFragment(){
        _navigateToFriendsFragment.value = true
    }

    fun navigateToMapFragment(){
        _navigateToMapFragment.value = true
    }

    fun navigateToDevicesFragment(){
        _navigateToDevicesFragment.value = true
    }

    fun navigateToConfigurationFragment(){
        _navigateToConfigurationFragment.value = true
    }

}
