package com.manegow.safeami.ui.login

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.*
import com.facebook.*
import com.firebase.ui.auth.AuthUI

import com.google.firebase.auth.*
import java.lang.Exception

class LoginViewModel(private val firebaseAuth: FirebaseAuth, application: Application) :
    AndroidViewModel(application) {

    private val _navigateToUserRegistration = MutableLiveData<Boolean>()
    val navigateToUserRegistration: LiveData<Boolean>
        get() = _navigateToUserRegistration

    private fun loginUser(username: String, password: String) {
        _navigateToUserRegistration.value = false
        firebaseAuth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //GO HOME
                    println("Successful login with email")
                } else {
                    _navigateToUserRegistration.value = true
                }
            }
    }

    fun onSignUpNavigated(){
        _navigateToUserRegistration.value = false
    }

    fun loginWithEmail(username: String, password: String) {
        loginUser(username, password)
    }

    fun handleFacebookAccessToken(token: AccessToken, context: Context) {

        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = firebaseAuth.currentUser
                    println("user: $user")
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
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

