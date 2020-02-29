package com.manegow.safeami.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.manegow.safeami.R
import com.manegow.safeami.database.SafeAmiDatabase
import com.manegow.safeami.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {

    private lateinit var shareViewModel: SignupViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingSignUp: FragmentSignupBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false)
        val application = requireNotNull(this.activity).application
        val databaseInstance = FirebaseDatabase.getInstance()
        val authentication = FirebaseAuth.getInstance()
        val dataSource = SafeAmiDatabase.getInstance(application).safeAmiDatabaseDao()
        val viewModelFactory = SignupViewModelFactory(databaseInstance, dataSource, authentication, this, application)
        shareViewModel = ViewModelProviders.of(this,viewModelFactory)
            .get(SignupViewModel::class.java)

        bindingSignUp.btnRegister.setOnClickListener {
            shareViewModel.registerUser(bindingSignUp.usernameInputEditText.text.toString(), bindingSignUp.emailInputEditText.text.toString(), bindingSignUp.passwordInputEditText.text.toString())
        }
        return bindingSignUp.root
    }
}