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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.manegow.safeami.BaseViewModel
import com.manegow.safeami.database.SafeAmiDatabaseDao
import com.manegow.safeami.database.User
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginViewModel(
    private val firebaseAuth: FirebaseAuth,
    private val datasource: SafeAmiDatabaseDao,
    private val databaseInstance: FirebaseDatabase,
    fragment: Fragment,
    application: Application
) :
    BaseViewModel(fragment, application) {

    private val _navigateToUserRegistration = MutableLiveData<Boolean>()
    val navigateToUserRegistration: LiveData<Boolean>
        get() = _navigateToUserRegistration

    private val _navigateToMainScreen = MutableLiveData<Boolean>()
    val navigateToMainScreen: LiveData<Boolean>
        get() = _navigateToMainScreen

    private fun loginUser(email: String, password: String) {
        _navigateToUserRegistration.value = false
        _navigateToMainScreen.value = false
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    getLocalUser()
                    onMainScreenNavigated()
                    println("Successful login with email")
                } else {
                    generateWarningAlert(
                        "No estas registrado",
                        "¿Deseas crear una nueva cuenta?",
                        "Ok"
                    )
                    _navigateToUserRegistration.value = true
                }
            }
    }

    private fun getLocalUser() {
        uiScope.launch {
            _navigateToMainScreen.value = false
            _navigateToUserRegistration.value = false
            val user = suspendGetCurrentUser(datasource)
            if (user != null) {
                _navigateToMainScreen.value = true
            } else {
                initUserFromFirebase()
                _navigateToMainScreen.value = true
            }
        }
    }

    private fun initUserFromFirebase() {
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val rootRef = FirebaseDatabase.getInstance().reference
        val uidRef = rootRef.child("Users").child(uid)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val user = p0.getValue(com.manegow.safeami.data.firebase.User::class.java)
                println("usuario: ${user!!.username}")
                println("usuario: ${user.email}")
                println("usuario: ${user.password}")
                createUserInRoom(datasource, user.username, user.email, user.password)
            }

            override fun onCancelled(p0: DatabaseError) {
                println("Error al obtener datos de Firebase $p0")
            }
        }
        uidRef.addListenerForSingleValueEvent(valueEventListener)
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

    override fun onCleared() {
        super.onCleared()
        uiScope.cancel()
    }
}

