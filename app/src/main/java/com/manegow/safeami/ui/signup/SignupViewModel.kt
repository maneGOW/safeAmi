package com.manegow.safeami.ui.signup

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class SignupViewModel(
    databaseInstance: FirebaseDatabase,
    autentication: FirebaseAuth,
    application: Application
) : AndroidViewModel(application) {

    private var databaseReference = databaseInstance.reference.child("Users")
    private var auth = autentication

    private fun createNewAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                val user: FirebaseUser = auth.currentUser!!
                verifyEmail(user)
                val currentUserDb = databaseReference.child(user.uid)
                currentUserDb.child("email").setValue(email)
                currentUserDb.child("password").setValue(password)
            }.addOnFailureListener {
                Toast.makeText(
                    getApplication(), "Error en la autenticación.",
                    Toast.LENGTH_SHORT
                ).show()
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

    fun registerUser(email: String, password: String) {
        createNewAccount(email, password)
    }
}