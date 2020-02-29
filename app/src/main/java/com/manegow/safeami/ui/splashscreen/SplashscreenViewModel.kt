package com.manegow.safeami.ui.splashscreen

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manegow.safeami.BaseViewModel
import com.manegow.safeami.database.SafeAmiDatabaseDao
import com.manegow.safeami.database.User
import kotlinx.coroutines.*

class SplashscreenViewModel(val database : SafeAmiDatabaseDao, application: Application) : BaseViewModel(
    Fragment(), application) {

    private val _navigateToLogin = MutableLiveData<Boolean>()
    val navigateToLogin : LiveData<Boolean>
    get() = _navigateToLogin

    private val _navigateToMainScreen = MutableLiveData<Boolean>()
    val navigateToMainScreen: LiveData<Boolean>
        get() = _navigateToMainScreen



    fun onMainScreenNavigated(){
        _navigateToMainScreen.value = false
    }

    fun onLoginNavigated(){
        _navigateToLogin.value = false
    }

    fun getLocalUser(){
        uiScope.launch {
            _navigateToMainScreen.value = false
            _navigateToLogin.value = false
            val user = suspendGetCurrentUser(database)
            if(user != null){
                _navigateToMainScreen.value = true
            }else _navigateToLogin.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        uiScope.cancel()
    }
}