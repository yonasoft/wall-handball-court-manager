package com.yonasoft.handballcourtmanager.fragments.details

import android.os.Bundle
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
import com.yonasoft.handballcourtmanager.databinding.FragmentSinglesDetailBinding
import com.yonasoft.handballcourtmanager.db.matchesdb.MatchType
import com.yonasoft.handballcourtmanager.fragments.details.dialogs.EndMatchDialogFragment
import com.yonasoft.handballcourtmanager.fragments.details.viewmodel.MatchDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SinglesDetailFragment : Fragment() {

    private var _binding: FragmentSinglesDetailBinding? = null
    private val binding get() = _binding!!

    private val args: SinglesDetailFragmentArgs by navArgs()
    private val matchDetailViewModel by viewModels<MatchDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_singles_detail, container, false)
        binding.viewModel = matchDetailViewModel // Here's the corrected line
        setupObservers()
        setUpListeners()
        return binding.root
    }

    private fun setUpListeners() {
        binding.apply {
            listOf(tvT1 to "t1", tvT2 to "t2").forEach { pair ->
                pair.first.setOnClickListener {
                    changeOrAddPlayer(pair.second)
                }
            }

            listOf(btnT1Add to "t1", btnT2Add to "t2").forEach { pair ->
                pair.first.setOnClickListener {
                    matchDetailViewModel.addPoints(pair.second)
                }
            }

            listOf(btnT1Sub to "t1", btnT2Sub to "t2").forEach { pair ->
                pair.first.setOnClickListener {
                    matchDetailViewModel.deductPoints(pair.second)
                }
            }

            btnEndMatch.setOnClickListener { endMatch() }
            editTextNum.apply {
                setOnClickListener { text!!.clear() }
                setOnFocusChangeListener { _, _ ->
                    matchDetailViewModel.updateCourtNum(text.toString())
                    isCursorVisible = false
                }
            }
        }
    }

    private fun endMatch() {
        val match = matchDetailViewModel.match.value!!
        if (match.teams[1]!![0] != "TBA" && match.teams[2]!![0] != "TBA") {
            findNavController().navigate(SinglesDetailFragmentDirections.actionSinglesDetailFragmentToEndMatchDialogFragment())
            setFragmentResultListener(EndMatchDialogFragment.REQUEST_KEY_END) { _, bundle ->
                if (bundle.getBoolean(EndMatchDialogFragment.BUNDLE_KEY_END)) {
                    findNavController().popBackStack()
                    findNavController().navigate(
                        SinglesDetailFragmentDirections.actionSinglesDetailFragmentToReturnToWinnersDialogFragment(
                            arrayOf(
                                match.teams[1]!![0],
                                match.teams[1]!![1],
                                match.teams[2]!![0],
                                match.teams[2]!![1],
                                match.teams[3]!![0]
                            ),
                            MatchType.SINGLES.name
                        )
                    )
                    matchDetailViewModel.completeMatch()
                }
            }
        } else {
            Toast.makeText(context, "Press the \"TBA\" to add a player!", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupObservers() {
        matchDetailViewModel.match.observe(viewLifecycleOwner) { match ->
            binding.apply {
                editTextNum.setText(match.courtNumber)
                tvT1.text = match.teams[1]!![0]
                tvT2.text = match.teams[2]!![0]
                tvT1Score.text = match.scores[1].toString()
                tvT2Score.text = match.scores[2].toString()
            }
        }
    }

    private fun changeOrAddPlayer(playerAndTeam: String) {
        findNavController().navigate(SinglesDetailFragmentDirections.actionSinglesDetailFragmentToSelectFromRosterFragment())
        setFragmentResultListener(SelectFromRosterFragment.REQUEST_KEY_PLAYER) { _, bundle ->
            val result = bundle.getString(SelectFromRosterFragment.BUNDLE_KEY_PLAYER)
            when (playerAndTeam) {
                "t1" -> matchDetailViewModel.match.value?.teams?.get(1)?.set(0, result.toString())
                "t2" -> matchDetailViewModel.match.value?.teams?.get(2)?.set(0, result.toString())
            }
            matchDetailViewModel.updateMatch()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

