package com.manegow.safeami.ui.profile

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.manegow.safeami.database.SafeAmiDatabaseDao

class ProfileViewModelFactory (private val databaseRoom: SafeAmiDatabaseDao, private val fragment: Fragment, private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProfileViewModel::class.java)){
            return ProfileViewModel(databaseRoom, fragment, application) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}