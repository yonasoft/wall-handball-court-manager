package com.yonasoft.handballcourtmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.yonasoft.handballcourtmanager.R
import com.yonasoft.handballcourtmanager.databinding.FragmentAddPlayerDialogBinding
import com.yonasoft.handballcourtmanager.viewmodel.RosterViewModel


class AddPlayerDialogFragment : DialogFragment() {

    lateinit var binding: FragmentAddPlayerDialogBinding
    private val viewModel: RosterViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        //Sets the size of the dialog
        dialog?.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_add_player_dialog, container, false
        )
        val view = binding.root

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        //Adds the player entered into queue
        binding.addPlayerButton.setOnClickListener {
            viewModel.addPlayer()
            dismiss()
        }
        return view
    }
}