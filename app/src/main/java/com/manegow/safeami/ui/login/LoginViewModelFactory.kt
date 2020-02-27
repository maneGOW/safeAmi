package com.manegow.safeami.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class LoginViewModelFactory(): ViewModelProvider.Factory{
    override  fun <T: ViewModel?>create(modelClass:Class<T>): T{
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return  LoginViewModel() as T
        }
        throw  IllegalArgumentException("Unknown Viewmodel class")
    }
}
