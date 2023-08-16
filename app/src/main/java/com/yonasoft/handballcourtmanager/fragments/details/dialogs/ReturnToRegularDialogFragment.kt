package com.yonasoft.handballcourtmanager.fragments.details.dialogs

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yonasoft.handballcourtmanager.R
import com.yonasoft.handballcourtmanager.databinding.FragmentReturnToQueueDialogBinding
import com.yonasoft.handballcourtmanager.fragments.roster.viewmodel.RosterViewModel
import dagger.hilt.android.AndroidEntryPoint

//Dialog that appears after the user presses yes to ending a match
@AndroidEntryPoint
class ReturnToRegularQueueFragmentDialogFragment : DialogFragment() {

    private var binding:FragmentReturnToQueueDialogBinding?=null
    private val viewModel: RosterViewModel by viewModels()
    private val args: ReturnToRegularQueueFragmentDialogFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_return_to_queue_dialog, container, false)
        setDialogSize()
        setViews()
        return binding?.root ?: View(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        //When the dialog is dismissed the user tis sent back to matches fragment
        findNavController().navigate(
            ReturnToRegularQueueFragmentDialogFragmentDirections.actionReturnToRegularQueueFragmentDialogFragmentToMatchesFragment()
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun setDialogSize() {
        dialog?.window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
    }

    private fun setListeners() {
        binding?.btnOk?.setOnClickListener {
            val sendToQueue = gatherCheckedPlayersAndUpdate()
            viewModel.addAllPlayers(sendToQueue, false)
            dismiss()
        }
    }

    private fun gatherCheckedPlayersAndUpdate(): MutableList<String> {
        val sendToQueue = mutableListOf<String>()
        binding?.let { bind ->
            bind.checkboxT1p1.updatePlayer(sendToQueue, 0)
            bind.checkboxT1p2.updatePlayer(sendToQueue, 1)
            bind.checkboxT2p1.updatePlayer(sendToQueue, 2)
            bind.checkboxT2p2.updatePlayer(sendToQueue, 3)
            bind.checkboxT3.updatePlayer(sendToQueue, 4)
        }
        return sendToQueue
    }

    private fun setViews() {
        binding?.apply {
            tvReturnToQueueMessage.text = getString(R.string.send_to_regular_msg)
            setCheckboxTextOrHide(checkboxT1p1, 0)
            setCheckboxTextOrHide(checkboxT1p2, 1)
            setCheckboxTextOrHide(checkboxT2p1, 2)
            setCheckboxTextOrHide(checkboxT2p2, 3)
            setCheckboxTextOrHide(checkboxT3, 4)
        }
    }

    private fun setCheckboxTextOrHide(checkbox: CheckBox, index: Int) {
        if (args.players[index] != "TBA") {
            checkbox.text = args.players[index]
        } else {
            checkbox.visibility = View.GONE
        }
    }

    private fun CheckBox.updatePlayer(sendToQueue: MutableList<String>, index: Int) {
        if (isChecked) {
            sendToQueue.add(args.players[index])
            args.players[index] = "TBA"
        }
    }
}