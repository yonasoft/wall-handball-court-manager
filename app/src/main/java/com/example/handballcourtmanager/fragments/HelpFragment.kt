package com.example.handballcourtmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.handballcourtmanager.R
import com.example.handballcourtmanager.databinding.FragmentHelpBinding

class HelpFragment:Fragment() {

    private var binding:FragmentHelpBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_help,container,false)
        val view = binding!!.root

        return view
    }
}