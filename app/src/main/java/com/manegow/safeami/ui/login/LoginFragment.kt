package com.manegow.safeami.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.manegow.safeami.R
import com.manegow.safeami.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var callbackManager: CallbackManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingLogin : FragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        val viewModelFactory = LoginViewModelFactory()

        val loginViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(LoginViewModel::class.java)

        bindingLogin.btnRecoverPassword.setOnClickListener { v: View ->
            v.findNavController().navigate(LoginFragmentDirections.actionNavLoginToRecoverPasswordFragment())
        }
        bindingLogin.btnSignUp.setOnClickListener { v: View ->
            v.findNavController().navigate(LoginFragmentDirections.actionNavLoginToNavSingup())
        }
        bindingLogin.btnLogin.setOnClickListener { v: View ->
            v.findNavController().navigate(LoginFragmentDirections.actionNavLoginToNavHome())
        }

        callbackManager = CallbackManager.Factory.create()
        bindingLogin.btnFacebookLogin.setReadPermissions("email", "public_profile")

        var auth = FirebaseAuth.getInstance()

        bindingLogin.btnFacebookLogin.registerCallback(callbackManager,  object : FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
                println("SUCCESSFUL LOGIN")
                loginViewModel.handleFacebookAccessToken(result!!.accessToken, context!!, auth)
            }

            override fun onCancel() {
                println("CANCEL FACEBOOK")
            }

            override fun onError(error: FacebookException?) {
                println("ERROR FACEBOOK")
            }
        })

        bindingLogin.btnFacebookLogin.setFragment(this)

        return bindingLogin.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager!!.onActivityResult(requestCode, resultCode, data);
    }
}