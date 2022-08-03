package com.example.handballcourtmanager.fragments

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
import com.example.handballcourtmanager.R
import com.example.handballcourtmanager.databinding.FragmentTriangleDetailBinding
import com.example.handballcourtmanager.db.matchesdb.MatchTypes
import com.example.handballcourtmanager.viewmodel.MatchDetailViewModel
import com.example.handballcourtmanager.viewmodel.MatchDetailViewModelFactory

class TriangleDetailFragment : Fragment() {

    private var binding: FragmentTriangleDetailBinding? = null
    private val args: TriangleDetailFragmentArgs by navArgs()
    private val matchDetailViewModel: MatchDetailViewModel by viewModels {
        MatchDetailViewModelFactory(args.matchId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_triangle_detail, container, false)
        val view = binding!!.root

        binding!!.viewModel = matchDetailViewModel
        setHasOptionsMenu(true)
        setupObservers()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
    }


    private fun setUpListeners() {

        binding!!.apply {
            tvT1.setOnClickListener {
                changeOrAddPlayer("t1")
            }
            tvT2.setOnClickListener {
                changeOrAddPlayer("t2")
            }
            tvT3.setOnClickListener {
                changeOrAddPlayer("t3")
            }
            btnT1Add.setOnClickListener {
                matchDetailViewModel.addPoints("t1")
            }
            btnT2Add.setOnClickListener {
                matchDetailViewModel.addPoints("t2")
            }
            btnT3Add.setOnClickListener {
                matchDetailViewModel.addPoints("t3")
            }
            btnT1Sub.setOnClickListener {
                matchDetailViewModel.deductPoints("t1")
            }
            btnT2Sub.setOnClickListener {
                matchDetailViewModel.deductPoints("t2")
            }
            btnT3Sub.setOnClickListener {
                matchDetailViewModel.deductPoints("t3")
            }

            btnEndMatch.setOnClickListener {
                val match = matchDetailViewModel.match!!.value!!
                if (match.teamOnePlayer1 != "TBA" && match.teamTwoPlayer1 != "TBA" && match.teamThreePlayer != "TBA") {
                    findNavController().navigate(
                        TriangleDetailFragmentDirections.actionFragmentTriangleDetailToEndMatchDialogFragment()
                    )

                    setFragmentResultListener(EndMatchDialogFragment.REQUEST_KEY_END) { _, bundle ->
                        val result = bundle.getBoolean(EndMatchDialogFragment.BUNDLE_KEY_END)

                        if (result) {

                            findNavController().popBackStack()

                            findNavController().navigate(
                                TriangleDetailFragmentDirections.actionFragmentTriangleDetailToReturnToWinnersDialogFragment(
                                    arrayOf(
                                        match.teamOnePlayer1,
                                        match.teamOnePlayer2,
                                        match.teamTwoPlayer1,
                                        match.teamTwoPlayer2,
                                        match.teamThreePlayer
                                    ),
                                    MatchTypes.TRIANGLE
                                )
                            )
                            matchDetailViewModel.completeMatch()

                        }

                    }
                } else {
                    Toast.makeText(context, "Press the \"TBA\" to add a player!", Toast.LENGTH_LONG)
                        .show()
                }
            }

            binding!!.editTextNum.apply {

                setOnClickListener {
                    binding!!.editTextNum.text!!.clear()
                }

                setImeActionLabel(binding!!.editTextNum.text.toString(), KeyEvent.KEYCODE_ENTER)

                setOnFocusChangeListener { _, _ ->
                    matchDetailViewModel.updateCourtNum(binding!!.editTextNum.text.toString())
                }

            }
        }
    }

    private fun setupObservers() {
        binding!!.viewModel!!.match!!.observe(viewLifecycleOwner) {
            binding!!.apply {
                editTextNum.setText(it.courtNumber)
                tvT1.text = it.teamOnePlayer1
                tvT2.text = it.teamTwoPlayer1
                tvT3.text = it.teamThreePlayer
                tvT1Score.text = it.teamOneScore.toString()
                tvT2Score.text = it.teamTwoScore.toString()
                tvT3Score.text = it.teamThreeScore.toString()
            }
        }
    }

    private fun changeOrAddPlayer(playerAndTeam: String) {
        findNavController().navigate(
            TriangleDetailFragmentDirections.actionFragmentTriangleDetailToSelectFromRosterFragment()
        )

        setFragmentResultListener(SelectFromRosterFragment.REQUEST_KEY_PLAYER) { _, bundle ->
            val result = bundle.getString(SelectFromRosterFragment.BUNDLE_KEY_PLAYER)
            when (playerAndTeam) {
                "t1" -> matchDetailViewModel.match!!.value!!.teamOnePlayer1 = result!!
                "t2" -> matchDetailViewModel.match!!.value!!.teamTwoPlayer1 = result!!
                "t3" -> matchDetailViewModel.match!!.value!!.teamThreePlayer = result!!
            }
            matchDetailViewModel.updateMatch()
        }
    }
}