package com.example.handballcourtmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.handballcourtmanager.R
import com.example.handballcourtmanager.databinding.FragmentAddPlayerDialogBinding
import com.example.handballcourtmanager.databinding.FragmentCreateMatchDialogBinding

class CreateMatchDialogFragment:DialogFragment(){

    lateinit var binding: FragmentCreateMatchDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(layoutInflater,
            R.layout.fragment_create_match_dialog,container,false)
        val view=binding.root


        return view
    }
}