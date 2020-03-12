package com.manegow.safeami.ui.home

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HomeViewModelFactory(
    private val fragment: Fragment,
    private val application: Application
): ViewModelProvider.Factory{
    override  fun <T: ViewModel?>create(modelClass:Class<T>): T{
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return  HomeViewModel(fragment, application ) as T
        }
        throw  IllegalArgumentException("Unknown Viewmodel class")
    }
}