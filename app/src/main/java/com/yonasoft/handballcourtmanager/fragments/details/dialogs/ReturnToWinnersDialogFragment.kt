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

    private var binding: FragmentReturnToQueueDialogBinding? = null
    private val viewModel: RosterViewModel by viewModels()
    private val args: ReturnToWinnersDialogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_return_to_queue_dialog, container, false)
        dialog?.window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        initializeViews()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun initializeViews() {
        binding?.apply {
            tvReturnToQueueMessage.text = getString(R.string.send_to_winner_msg)

            listOf(checkboxT1p1, checkboxT1p2, checkboxT2p1, checkboxT2p2, checkboxT3).forEachIndexed { index, checkbox ->
                if (args.players[index] != "TBA") {
                    checkbox.text = args.players[index]
                } else {
                    checkbox.visibility = View.GONE
                }
            }
        }
    }

    private fun setupListeners() {
        binding?.btnOk?.setOnClickListener {
            val sendToQueue: MutableList<String> = mutableListOf()

            binding?.apply {
                listOf(checkboxT1p1, checkboxT1p2, checkboxT2p1, checkboxT2p2, checkboxT3).forEachIndexed { index, checkbox ->
                    if (checkbox.isChecked) {
                        sendToQueue.add(args.players[index])
                        args.players[index] = "TBA"
                    }
                }
            }
            viewModel.addAllPlayers(sendToQueue, true)
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val navigateTo = when(args.matchType) {
            MatchType.SINGLES.name -> SinglesDetailFragmentDirections.actionSinglesDetailFragmentToReturnToRegularQueueFragmentDialogFragment(args.players)
            MatchType.DOUBLES.name -> DoublesDetailFragmentDirections.actionFragmentDoublesDetailToReturnToRegularQueueFragmentDialogFragment(args.players)
            MatchType.TRIANGLE.name -> TriangleDetailFragmentDirections.actionFragmentTriangleDetailToReturnToRegularQueueFragmentDialogFragment(args.players)
            else -> throw IllegalArgumentException("Incorrect Match Type")
        }
        findNavController().apply {
            popBackStack()
            navigate(navigateTo)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
