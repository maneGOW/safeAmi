package com.manegow.safeami.ui.frienddetails

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.manegow.safeami.R


class FriendDetails : Fragment() {

    companion object {
        fun newInstance() = FriendDetails()
    }

    private lateinit var viewModel: FriendDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_friend_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FriendDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
