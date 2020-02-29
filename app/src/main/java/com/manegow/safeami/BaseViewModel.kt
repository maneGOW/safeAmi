package com.manegow.safeami

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar
import com.manegow.safeami.database.Device
import com.manegow.safeami.database.SafeAmiDatabaseDao
import com.manegow.safeami.database.User
import kotlinx.coroutines.*

open class BaseViewModel(private val fragment: Fragment, application: Application): AndroidViewModel(application){

    private val viewModelJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

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

    private suspend fun suspendCreateUser(database: SafeAmiDatabaseDao,user: User) {
        withContext(Dispatchers.IO) {
            database.insertUser(user)
        }
    }

     fun createUserInRoom(database: SafeAmiDatabaseDao, username: String, email: String, password: String) {
        uiScope.launch {
            val user = User(0, username, email, password, System.currentTimeMillis(), 0)
            suspendCreateUser(database, user)
            println("usuario $username creado en room")
        }
    }

    suspend fun suspendGetDevices(database: SafeAmiDatabaseDao): LiveData<List<Device>>?{
        return withContext(Dispatchers.IO){
            database.getAllDevices()
        }
    }

    suspend fun suspendGetCurrentUser(database: SafeAmiDatabaseDao): User?{
        return withContext(Dispatchers.IO){
            database.getUser(1)
        }
    }
}