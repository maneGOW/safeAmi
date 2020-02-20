package com.manegow.safeami.ui.singup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.manegow.safeami.R

class SingupFragment : Fragment() {

    private lateinit var shareViewModel: SingupViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        shareViewModel =
            ViewModelProviders.of(this).get(SingupViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_share, container, false)

        return root
    }
}