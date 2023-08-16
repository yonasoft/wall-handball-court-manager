package com.yonasoft.handballcourtmanager.fragments.roster.dialog

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
import com.yonasoft.handballcourtmanager.fragments.roster.viewmodel.RosterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPlayerDialogFragment : DialogFragment() {

    private var binding: FragmentAddPlayerDialogBinding?=null
    private val viewModel: RosterViewModel by viewModels()

    override fun onStart() {
        super.onStart()
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
            R.layout.fragment_add_player_dialog,
            container,
            false
        )

        return binding?.apply {
            viewModel = this@AddPlayerDialogFragment.viewModel
            lifecycleOwner = this@AddPlayerDialogFragment
            addPlayerButton.setOnClickListener {
                this.viewModel?.addPlayer()
                dismiss()
            }
        }?.root?: throw IllegalStateException("View binding is null")
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}