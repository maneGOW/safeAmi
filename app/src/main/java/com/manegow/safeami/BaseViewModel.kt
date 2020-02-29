package com.manegow.safeami

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import com.google.android.material.snackbar.Snackbar

open class BaseViewModel(private val fragment: Fragment, application: Application): AndroidViewModel(application){

    fun generateWarningAlert(title:String, message:String, buttonText:String){
        val alertBuilder = AlertDialog.Builder(fragment.context)
        alertBuilder.setTitle(title)
        alertBuilder.setMessage(message)
        alertBuilder.setPositiveButton(buttonText) { _, _ -> }
        val alertDialog: AlertDialog = alertBuilder.create()
        alertDialog.show()
    }

    fun generateToast(message: String){
        Toast.makeText(fragment.context, message, Toast.LENGTH_SHORT).show()
    }

    fun generateSnackBar(message: String){
        Snackbar.make(fragment.view!!, message, Snackbar.LENGTH_LONG)
    }
}