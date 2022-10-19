package com.yonasoft.handballcourtmanager.fragments.details.dialogs

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yonasoft.handballcourtmanager.R
import com.yonasoft.handballcourtmanager.databinding.FragmentReturnToQueueDialogBinding
import com.yonasoft.handballcourtmanager.db.matchesdb.MatchType
import com.yonasoft.handballcourtmanager.fragments.details.DoublesDetailFragmentDirections
import com.yonasoft.handballcourtmanager.fragments.details.SinglesDetailFragmentDirections
import com.yonasoft.handballcourtmanager.fragments.details.TriangleDetailFragmentDirections
import com.yonasoft.handballcourtmanager.fragments.roster.viewmodel.RosterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReturnToWinnersDialogFragment : DialogFragment() {

    private var binding: FragmentReturnToQueueDialogBinding?=null
    private val viewModel: RosterViewModel by viewModels()
    private val args: ReturnToWinnersDialogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_return_to_queue_dialog,
            container,
            false
        )
        //Set layout size
        dialog?.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        //Sets the views
        setViews()

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        binding!!.btnOk.setOnClickListener {
            val sendToQueue: MutableList<String> = mutableListOf()
            if (binding!!.checkboxT1p1.isChecked) {
                sendToQueue.add(args.players[0])
                args.players[0] = "TBA"
            }
            if (binding!!.checkboxT1p2.isChecked) {
                sendToQueue.add(args.players[1])
                args.players[1] = "TBA"
            }
            if (binding!!.checkboxT2p1.isChecked) {
                sendToQueue.add(args.players[2])
                args.players[2] = "TBA"
            }
            if (binding!!.checkboxT2p2.isChecked) {
                sendToQueue.add(args.players[3])
                args.players[3] = "TBA"
            }
            if (binding!!.checkboxT3.isChecked) {
                sendToQueue.add(args.players[4])
                args.players[4] = "TBA"
            }
            viewModel.addAllPlayers(sendToQueue, true)
            dismiss()
        }
    }

    private fun setViews() {
        binding!!.apply {
            //Message for the dialog
            tvReturnToQueueMessage.text = getString(R.string.send_to_winner_msg)
            //All the players available and eligible to return to regular queue will be displayed in their respective checkbox
            if (args.players[0] != "TBA") checkboxT1p1.text =
                args.players[0] else binding!!.checkboxT1p1.visibility = View.GONE
            if (args.players[1] != "TBA") checkboxT1p2.text =
                args.players[1] else binding!!.checkboxT1p2.visibility = View.GONE
            if (args.players[2] != "TBA") checkboxT2p1.text =
                args.players[2] else binding!!.checkboxT2p1.visibility = View.GONE
            if (args.players[3] != "TBA") checkboxT2p2.text =
                args.players[3] else binding!!.checkboxT2p2.visibility = View.GONE
            if (args.players[4] != "TBA") checkboxT3.text =
                args.players[4] else binding!!.checkboxT3.visibility = View.GONE
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val players = arrayOf(
            args.players[0], args.players[1], args.players[2], args.players[3], args.players[4]
        )
        val navigateTo = when(
            args.matchType
        ){
            MatchType.SINGLES.name-> SinglesDetailFragmentDirections.actionSinglesDetailFragmentToReturnToRegularQueueFragmentDialogFragment(
                players
            )
            MatchType.DOUBLES.name-> DoublesDetailFragmentDirections.actionFragmentDoublesDetailToReturnToRegularQueueFragmentDialogFragment(
                players
            )
            MatchType.TRIANGLE.name-> TriangleDetailFragmentDirections.actionFragmentTriangleDetailToReturnToRegularQueueFragmentDialogFragment(
                players
            )
            else -> throw IllegalArgumentException("Incorrect Match Type")
        }
        findNavController().popBackStack()
        findNavController().navigate(
            navigateTo
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}