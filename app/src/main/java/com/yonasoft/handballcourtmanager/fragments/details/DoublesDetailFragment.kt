package com.yonasoft.handballcourtmanager.fragments.details

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yonasoft.handballcourtmanager.R
import com.yonasoft.handballcourtmanager.databinding.FragmentDoublesDetailBinding
import com.yonasoft.handballcourtmanager.db.matchesdb.Match
import com.yonasoft.handballcourtmanager.db.matchesdb.MatchType
import com.yonasoft.handballcourtmanager.fragments.details.dialogs.EndMatchDialogFragment
import com.yonasoft.handballcourtmanager.fragments.details.viewmodel.MatchDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoublesDetailFragment : Fragment() {

    private var binding: FragmentDoublesDetailBinding? = null
    private val args: DoublesDetailFragmentArgs by navArgs()
    private val matchDetailViewModel: MatchDetailViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return DataBindingUtil.inflate<FragmentDoublesDetailBinding>(inflater, R.layout.fragment_doubles_detail, container, false).apply {
            viewModel = matchDetailViewModel
        }.also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setUpListeners()
    }

    private fun setUpListeners() {
        binding?.apply {
            tvT1P1.setOnClickListener { changeOrAddPlayer(TEAM_1_PLAYER_1) }
            tvT1P2.setOnClickListener { changeOrAddPlayer(TEAM_1_PLAYER_2) }
            tvT2P1.setOnClickListener { changeOrAddPlayer(TEAM_2_PLAYER_1) }
            tvT2P2.setOnClickListener { changeOrAddPlayer(TEAM_2_PLAYER_2) }

            btnT1Add.setOnClickListener { matchDetailViewModel.addPoints(TEAM_1) }
            btnT2Add.setOnClickListener { matchDetailViewModel.addPoints(TEAM_2) }
            btnT1Sub.setOnClickListener { matchDetailViewModel.deductPoints(TEAM_1) }
            btnT2Sub.setOnClickListener { matchDetailViewModel.deductPoints(TEAM_2) }

            btnEndMatch.setOnClickListener { handleEndMatchClick() }

            setupCourtNumberInteraction()
        }
    }

    private fun setupCourtNumberInteraction() {
        binding?.editTextNum?.apply {
            setOnClickListener { text?.clear() }
            setImeActionLabel(text.toString(), KeyEvent.KEYCODE_ENTER)
            setOnFocusChangeListener { _, _ ->
                matchDetailViewModel.updateCourtNum(text.toString())
                isCursorVisible = false
            }
        }
    }

    private fun handleEndMatchClick() {
        val match = matchDetailViewModel.match.value ?: return

        if (allPlayersFilled(match)) {
            navigateToEndMatchConfirmation()
            setEndMatchResultListener()
        } else {
            Toast.makeText(context, "Press the \"TBA\" to add a player!", Toast.LENGTH_LONG).show()
        }
    }

    private fun allPlayersFilled(match: Match): Boolean {
        return match.teams[1]?.all { it != "TBA" } == true && match.teams[2]?.all { it != "TBA" } == true
    }

    private fun navigateToEndMatchConfirmation() {
        findNavController().navigate(DoublesDetailFragmentDirections.actionFragmentDoublesDetailToEndMatchDialogFragment())
    }

    private fun setEndMatchResultListener() {
        setFragmentResultListener(EndMatchDialogFragment.REQUEST_KEY_END) { _, bundle ->
            val result = bundle.getBoolean(EndMatchDialogFragment.BUNDLE_KEY_END)
            if (result) {
                navigateToWinnerQueue()
                matchDetailViewModel.completeMatch()
            }
        }
    }

    private fun navigateToWinnerQueue() {
        val match = matchDetailViewModel.match.value!!
        findNavController().popBackStack()
        findNavController().navigate(
            DoublesDetailFragmentDirections.actionFragmentDoublesDetailToReturnToWinnersDialogFragment(
                arrayOf(
                    match.teams[1]!![0],
                    match.teams[1]!![1],
                    match.teams[2]!![0],
                    match.teams[2]!![1],
                    match.teams[3]!![0]
                ),
                MatchType.DOUBLES.name
            )
        )
    }

    private fun setupObservers() {
        binding?.viewModel?.match?.observe(viewLifecycleOwner) {
            updateViews(it)
        }
    }

    private fun updateViews(match: Match) {
        binding?.apply {
            tvT1P1.text = match.teams[1]?.get(0)
            tvT1P2.text = match.teams[1]?.get(1)
            tvT2P1.text = match.teams[2]?.get(0)
            tvT2P2.text = match.teams[2]?.get(1)
            tvT1Score.text = match.scores[1]?.toString()
            tvT2Score.text = match.scores[2]?.toString()
        }
    }

    private fun changeOrAddPlayer(playerAndTeam: String) {
        findNavController().navigate(DoublesDetailFragmentDirections.actionFragmentDoublesDetailToSelectFromRosterFragment())

        setFragmentResultListener(SelectFromRosterFragment.REQUEST_KEY_PLAYER) { _, bundle ->
            val result = bundle.getString(SelectFromRosterFragment.BUNDLE_KEY_PLAYER) ?: return@setFragmentResultListener
            when (playerAndTeam) {
                TEAM_1_PLAYER_1 -> matchDetailViewModel.match.value?.teams!![1]?.set(0, result)
                TEAM_1_PLAYER_2 -> matchDetailViewModel.match.value?.teams!![1]?.set(1, result)
                TEAM_2_PLAYER_1 -> matchDetailViewModel.match.value?.teams!![2]?.set(0, result)
                TEAM_2_PLAYER_2 -> matchDetailViewModel.match.value?.teams!![2]?.set(1, result)
            }
            matchDetailViewModel.updateMatch()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        const val TEAM_1_PLAYER_1 = "t1p1"
        const val TEAM_1_PLAYER_2 = "t1p2"
        const val TEAM_2_PLAYER_1 = "t2p1"
        const val TEAM_2_PLAYER_2 = "t2p2"

        const val TEAM_1 = "t1"
        const val TEAM_2 = "t2"
    }
}
