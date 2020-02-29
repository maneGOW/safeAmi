package com.manegow.safeami.ui.login

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.facebook.*
import com.firebase.ui.auth.AuthUI

import com.google.firebase.auth.*
import com.manegow.safeami.BaseViewModel
import java.lang.Exception

class LoginViewModel(private val firebaseAuth: FirebaseAuth, fragment: Fragment, application: Application) :
    BaseViewModel(fragment, application) {

    private val _navigateToUserRegistration = MutableLiveData<Boolean>()
    val navigateToUserRegistration: LiveData<Boolean>
        get() = _navigateToUserRegistration

    private val _navigateToMainScreen = MutableLiveData<Boolean>()
    val navigateToMainScreen: LiveData<Boolean>
        get() = _navigateToMainScreen

    private fun loginUser(username: String, password: String) {
        _navigateToUserRegistration.value = false
        _navigateToMainScreen.value = false
        firebaseAuth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _navigateToMainScreen.value = true
                    println("Successful login with email")
                } else {
                    generateWarningAlert("No estas registrado", "¿Deseas crear una nueva cuenta?", "Ok")
                    _navigateToUserRegistration.value = true
                }
            }
    }

    fun onSignUpNavigated() {
        _navigateToUserRegistration.value = false
    }

    fun onMainScreenNavigated() {
        _navigateToMainScreen.value = false
    }

    fun loginWithEmail(username: String, password: String) {
        if (!username.isNullOrBlank()) {
            if (!password.isNullOrBlank()) {
                loginUser(username, password)
            } else {
                generateToast("Tienes que escribir tu contraseña")
            }
        } else {
            generateToast("Tienes que escribir tu email")
        }
    }

    fun handleFacebookAccessToken(token: AccessToken, context: Context) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    validateFacebookUser(user)
                    updateUI(user)
                } else {
                    generateToast("No se pudo ingresar con tu perfil de FB")
                    updateUI(null)
                }
            }
    }

    private fun validateFacebookUser(user: FirebaseUser?) {
        if (user != null) {
            if (!user.displayName.isNullOrBlank() && !user.email.isNullOrBlank()) {
                generateToast("Hola $user")
                _navigateToMainScreen.value = true
            } else if (user.email.isNullOrBlank()) {
                generateWarningAlert(
                    "Hey!",
                    "Necesitamos que registres tu correo para poder continuar",
                    "OK"
                )
                _navigateToUserRegistration.value = true
            }
        } else {
            generateWarningAlert("Error", "Hubo un error al obtener tu usuario de FB", "Ok")
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            println(user.displayName)
            println(user.email)
            println(user.phoneNumber)
            println(user.photoUrl)
            println(user.uid)
        } else {
            println("NONE")
        }
    }
}

