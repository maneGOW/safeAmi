package com.manegow.safeami.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.manegow.safeami.R
import com.manegow.safeami.database.SafeAmiDatabase
import com.manegow.safeami.database.SafeAmiDatabaseDao
import com.manegow.safeami.databinding.FragmentProfileBinding
import com.manegow.safeami.ui.splashscreen.SplashscreenViewModel

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingProfile : FragmentProfileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        bindingProfile.setLifecycleOwner (this)
        val application = requireNotNull(this.activity).application
        val dataSource = SafeAmiDatabase.getInstance(application).safeAmiDatabaseDao()

        val viewModelFactory = ProfileViewModelFactory(dataSource, this, application)
        val profileViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ProfileViewModel::class.java)

        bindingProfile.profileViewModel = profileViewModel

        profileViewModel.getLocalUser()

        profileViewModel.usernameLiveData.observe(viewLifecycleOwner, Observer {
            username ->
            bindingProfile.txtUsername.text = username
        })

        profileViewModel.emailLivedata.observe(viewLifecycleOwner, Observer{
            email ->
            bindingProfile.txtEmail.text = email
        })

        return bindingProfile.root
    }
}