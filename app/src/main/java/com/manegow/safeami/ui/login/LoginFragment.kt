package com.manegow.safeami.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.manegow.safeami.R
import com.manegow.safeami.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingLogin : FragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        bindingLogin.btnRecoverPassword.setOnClickListener { v: View ->
            v.findNavController().navigate(LoginFragmentDirections.actionNavLoginToRecoverPasswordFragment())
        }
        bindingLogin.btnSignUp.setOnClickListener { v: View ->
            v.findNavController().navigate(LoginFragmentDirections.actionNavLoginToNavSingup())
        }
        bindingLogin.btnLogin.setOnClickListener { v: View ->
            v.findNavController().navigate(LoginFragmentDirections.actionNavLoginToNavHome())
        }
        return bindingLogin.root
    }
}