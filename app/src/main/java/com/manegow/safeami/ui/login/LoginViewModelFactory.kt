package com.manegow.safeami.ui.login

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.manegow.safeami.MyApplication
import com.manegow.safeami.database.SafeAmiDatabaseDao
import java.lang.IllegalArgumentException

class LoginViewModelFactory(
    private val firebaseAuth: FirebaseAuth,
    private val datasource: SafeAmiDatabaseDao,
    private val firebaseDataSource: FirebaseDatabase,
    private val fragment: Fragment,
    private val application: Application): ViewModelProvider.Factory{
    override  fun <T: ViewModel?>create(modelClass:Class<T>): T{
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return  LoginViewModel(firebaseAuth, datasource, firebaseDataSource, fragment, application ) as T
        }
        throw  IllegalArgumentException("Unknown Viewmodel class")
    }
}
