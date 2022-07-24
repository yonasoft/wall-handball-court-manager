package com.example.handballcourtmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.handballcourtmanager.R
import com.example.handballcourtmanager.databinding.FragmentAddPlayerDialogBinding
import com.example.handballcourtmanager.viewmodel.RosterViewModel


class AddPlayerDialogFragment:DialogFragment() {

    lateinit var binding: FragmentAddPlayerDialogBinding
    private val viewModel: RosterViewModel by viewModels()
    var name:String ?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(layoutInflater,
            R.layout.fragment_add_player_dialog,container,false)
        val view=binding.root
        binding.viewModel = viewModel

        binding.addPlayerButton.setOnClickListener {
            //val name = binding.editTextAddPlayer.text.toString()
            viewModel.addPlayer()
            dismiss()
        }

        return view
    }


}