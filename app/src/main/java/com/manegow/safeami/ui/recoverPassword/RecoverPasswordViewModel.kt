package com.manegow.safeami.ui.recoverPassword

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.FirebaseAuth

class RecoverPasswordViewModel(auth: FirebaseAuth, application: Application) :
    AndroidViewModel(application) {

    var authentication = auth
    private fun sendPasswordReset(email: String) {
        authentication.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(getApplication(), "Email Enviado", Toast.LENGTH_SHORT).show()
                    //navigate to home
                } else {
                    Toast.makeText(
                        getApplication(),
                        "No se encontró el usuario con este correo",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
    }

    fun passwordReset(email: String){
        sendPasswordReset(email)
    }
}
