package com.manegow.safeami.ui.friendrequest

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.manegow.safeami.R


class FriendRequestFragment : Fragment() {

    companion object {
        fun newInstance() =
            FriendRequestFragment()
    }

    private lateinit var viewModel: FriendRequestViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_friend_request, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FriendRequestViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
