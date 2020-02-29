package com.manegow.safeami.ui.splashscreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manegow.safeami.database.SafeAmiDatabaseDao
import com.manegow.safeami.database.User
import kotlinx.coroutines.*

class SplashscreenViewModel(val database : SafeAmiDatabaseDao, application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToLogin = MutableLiveData<Boolean>()
    val navigateToLogin : LiveData<Boolean>
    get() = _navigateToLogin

    private val _navigateToMainScreen = MutableLiveData<Boolean>()
    val navigateToMainScreen: LiveData<Boolean>
        get() = _navigateToMainScreen

    fun getLocalUser(){
        uiScope.launch {
            _navigateToMainScreen.value = false
            _navigateToLogin.value = false
            val user = getUserFromDatabase(1)
            if(user != null){
                _navigateToMainScreen.value = true
            }else _navigateToLogin.value = true
        }
    }

    fun onMainScreenNavigated(){
        _navigateToMainScreen.value = false
    }

    fun onLoginNavigated(){
        _navigateToLogin.value = false
    }

    private suspend fun getUserFromDatabase(id:Long): User? {
        return withContext(Dispatchers.IO){
             database.getUser(id)
        }
    }



}



/*
package com.manegow.coin

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class splashscreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragmentz
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        return inflater.inflate(R.layout.fragment_splashscreen, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed({
            context?.let {
                if(true){
                    findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                }
                else{
                    findNavController().navigate(R.id.action_splashscreen_to_paymentFragment)
                }

            }
        }, 2500)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }
}

 */