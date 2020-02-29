package com.manegow.safeami.ui.profile

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manegow.safeami.BaseViewModel
import com.manegow.safeami.database.SafeAmiDatabaseDao
import com.manegow.safeami.database.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(
    val database: SafeAmiDatabaseDao,
    fragment: Fragment,
    application: Application
) : BaseViewModel(fragment, application) {

    private val _emailLivedata = MutableLiveData<String>()
    val emailLivedata: LiveData<String>
        get() = _emailLivedata

    private val _usernameLiveData = MutableLiveData<String>()
    val usernameLiveData: LiveData<String>
        get() = _usernameLiveData

    fun getLocalUser(){
        uiScope.launch {
            val userData = suspendGetCurrentUser(database)
            if(userData != null){
                _usernameLiveData.value = userData.username
                _emailLivedata.value = userData.email
            }
        }
    }

    fun getDevices(){
        uiScope.launch {
            val devices =suspendGetDevices(database)
            if(devices != null){

            }
            else{

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        uiScope.cancel()
    }
}