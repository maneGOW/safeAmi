package com.manegow.safeami.ui.signup

import android.app.Application
import android.content.Context
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.manegow.safeami.BaseViewModel
import com.manegow.safeami.data.request.UserRegistrationRequest
import com.manegow.safeami.database.SafeAmiDatabase
import com.manegow.safeami.database.SafeAmiDatabaseDao
import com.manegow.safeami.database.User
import com.manegow.safeami.network.SafeAmiApi
import kotlinx.coroutines.*
import retrofit2.await
import java.lang.Exception

class SignupViewModel(
    databaseInstance: FirebaseDatabase,
    val databaseRoom: SafeAmiDatabaseDao,
    val autentication: FirebaseAuth,
    fragment: Fragment,
    application: Application
) : BaseViewModel(fragment, application) {

    private var databaseReference = databaseInstance.reference.child("Users")

    private fun createNewAccount(username: String, email: String, password: String) {
        autentication.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                val user: FirebaseUser = autentication.currentUser!!
                verifyEmail(user)
                createUserInstance(user, username, email, password)
            }.addOnFailureListener {
                Toast.makeText(
                    getApplication(), "Error en la autenticación.",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun createUserInstance(
        user: FirebaseUser,
        username: String,
        email: String,
        password: String
    ) {
        createUserInFirebaseDB(user, username, email, password)
        createUserInRoom(databaseRoom,username, email, password)
    }

    fun registerUser(username: String, email: String, password: String) {
        if (!username.isNullOrBlank()) {
            if (!email.isNullOrBlank()) {
                if (!password.isNullOrBlank()) {
                    createNewAccount(username, email, password)
                } else generateToast("Tienes que escribir un password.")
            } else generateToast("Tienes que escribir tu email.")
        } else generateToast("Tienes que escribir un username.")
    }

    private fun createUserInFirebaseDB(
        user: FirebaseUser,
        username: String,
        email: String,
        password: String
    ) {
        val currentUserDb = databaseReference.child(user.uid)
        currentUserDb.child("username").setValue(username)
        currentUserDb.child("email").setValue(email)
        currentUserDb.child("password").setValue(password)
        currentUserDb.child("created").setValue(System.currentTimeMillis())
        currentUserDb.child("deviceId").setValue(Build.ID)
        currentUserDb.child("device").setValue(Build.DEVICE)
        currentUserDb.child("deviceModel").setValue(Build.MODEL)
        currentUserDb.child("isActive").setValue(true)
        println("usuario $username creado en firebase")
    }

    private fun createUserInSafeAmiApi(username: String, email: String, password: String) {
        uiScope.launch {
            val request = UserRegistrationRequest(
                username,
                email,
                password,
                Build.ID,
                Build.DEVICE,
                Build.MODEL,
                "",
                true
            )
            val sendRequestDeferred =
                SafeAmiApi.amiApi.retrofitService.registerUserOnApi(request)
            try {
                val result = sendRequestDeferred.await()

                if (result.status == "0") {
                    generateWarningAlert("Registro", result.description, "Ok")
                } else {
                    println("error al crear usuario en mongo")
                }
            } catch (e: Exception) {
                println("error: $e")
                generateToast("Error de conexión, verifica que estés conectado a internet")
            }
        }
    }

    private fun verifyEmail(user: FirebaseUser) {
        user.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        getApplication(),
                        "Email " + user.email,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        getApplication(),
                        "Error al verificar el correo ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    override fun onCleared() {
        super.onCleared()
        uiScope.cancel()
    }
}