package com.manegow.safeami.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.manegow.safeami.R
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
        val viewModelFactory = SignUpViewModelFactory(databaseInstance, authentication, application)
    }
}