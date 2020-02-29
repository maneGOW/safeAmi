package com.manegow.safeami.ui.signup

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.manegow.safeami.database.SafeAmiDatabaseDao
import java.lang.IllegalArgumentException

class SignupViewModelFactory (private val databaseInstance: FirebaseDatabase,
                              private val databaseRoom: SafeAmiDatabaseDao,
                              private val authentication: FirebaseAuth,
                              private val context: Fragment,
                              private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SignupViewModel::class.java)){
            return SignupViewModel(databaseInstance, databaseRoom, authentication, context, application) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}