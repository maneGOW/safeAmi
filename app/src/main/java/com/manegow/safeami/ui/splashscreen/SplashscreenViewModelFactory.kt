package com.manegow.safeami.ui.splashscreen

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.manegow.safeami.database.SafeAmiDatabaseDao

class SplashscreenViewModelFactory (private val databaseRoom: SafeAmiDatabaseDao, private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SplashscreenViewModel::class.java)){
            return SplashscreenViewModel(databaseRoom, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}