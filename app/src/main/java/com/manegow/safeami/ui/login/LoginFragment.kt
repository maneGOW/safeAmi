package com.manegow.safeami.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.manegow.safeami.R
import com.manegow.safeami.data.AuthType
import com.manegow.safeami.databinding.FragmentLoginBinding
import com.manegow.safeami.helper.CustomMaterialDialog.showSimpleDialog
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private companion object{
        private  val facebook_permissions = mutableListOf("email", "public_profile")
    }

    private lateinit var callbackManager: CallbackManager
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingLogin : FragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        val application = requireNotNull(this.activity).application
        auth = FirebaseAuth.getInstance()

        val viewModelFactory = LoginViewModelFactory(auth, application)

        callbackManager = CallbackManager.Factory.create()

        loginViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(LoginViewModel::class.java)

        bindingLogin.btnRecoverPassword.setOnClickListener { v: View ->
            v.findNavController().navigate(LoginFragmentDirections.actionNavLoginToRecoverPasswordFragment())
        }
        bindingLogin.btnSignUp.setOnClickListener { v: View ->
            v.findNavController().navigate(LoginFragmentDirections.actionNavLoginToNavSingup())
        }
        loginViewModel.navigateToUserRegistration.observe(viewLifecycleOwner, Observer {
            navigate ->
            if(navigate){
                this.findNavController().navigate(
                    LoginFragmentDirections
                        .actionNavLoginToNavSingup())
                loginViewModel.onSignUpNavigated()
            }
        })

        bindingLogin.btnLogin.setOnClickListener { v: View ->
            //v.findNavController().navigate(LoginFragmentDirections.actionNavLoginToNavHome())
            loginViewModel.loginWithEmail(bindingLogin.usernameInputEditText.text.toString(), bindingLogin.passwordInputEditText.text.toString())

            /*
                    sleepTrackerViewModel.navigateToSleepDataQuality.observe(this, Observer { night ->
            night?.let {

                this.findNavController().navigate(
                        SleepTrackerFragmentDirections
                                .actionSleepTrackerFragmentToSleepDetailFragment(night))
                sleepTrackerViewModel.onSleepDataQualityNavigated()
            }
        })
             */
        }
        bindingLogin.btnFacebookLogin.setPermissions("email", "public_profile")

        bindingLogin.btnFacebookLogin.registerCallback(callbackManager, object : FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
                println("SUCCESS")
                loginViewModel.handleFacebookAccessToken(result!!.accessToken, application.applicationContext)
            }

            override fun onCancel() {
                println("CANCEL")
            }

            override fun onError(error: FacebookException?) {
                println("ERROR")
            }
        })

        bindingLogin.btnFacebookLogin.fragment = this

        return bindingLogin.root
    }

    override fun onStart() {
        super.onStart()
        auth.signOut()
        /*val currentUser = auth.currentUser
        Toast.makeText(this.context,"user $currentUser has logged in", Toast.LENGTH_LONG).show()*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}