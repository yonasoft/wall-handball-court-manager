package com.example.handballcourtmanager.fragments

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.handballcourtmanager.R
import com.example.handballcourtmanager.databinding.FragmentSinglesDetailBinding
import com.example.handballcourtmanager.db.matchesdb.MatchTypes
import com.example.handballcourtmanager.viewmodel.MatchDetailViewModel
import com.example.handballcourtmanager.viewmodel.MatchDetailViewModelFactory

class SinglesDetailFragment : Fragment() {

    private var binding: FragmentSinglesDetailBinding? = null
    private val args: SinglesDetailFragmentArgs by navArgs()
    private val matchDetailViewModel: MatchDetailViewModel by viewModels {
        MatchDetailViewModelFactory(args.matchId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_singles_detail, container, false)
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
            btnT1Add.setOnClickListener {
                matchDetailViewModel.addPoints("t1")
            }
            btnT2Add.setOnClickListener {
                matchDetailViewModel.addPoints("t2")
            }
            btnT1Sub.setOnClickListener {
                matchDetailViewModel.deductPoints("t1")
            }
            btnT2Sub.setOnClickListener {
                matchDetailViewModel.deductPoints("t2")
            }

            btnEndMatch.setOnClickListener{
                val match = matchDetailViewModel.match!!.value!!
                findNavController().navigate(SinglesDetailFragmentDirections.actionSinglesDetailFragmentToEndMatchDialogFragment())

                setFragmentResultListener(EndMatchDialogFragment.REQUEST_KEY_END){ _, bundle ->
                    val result = bundle.getBoolean(EndMatchDialogFragment.BUNDLE_KEY_END)

                    if(result) {

                        findNavController().popBackStack()

                        findNavController().navigate(SinglesDetailFragmentDirections.actionSinglesDetailFragmentToReturnToWinnersDialogFragment(
                            arrayOf(
                                match.teamOnePlayer1,match.teamOnePlayer2,match.teamTwoPlayer1,match.teamTwoPlayer2,match.teamThreePlayer
                            ),
                            MatchTypes.SINGLES
                        ))
                        matchDetailViewModel.completeMatch()

                    }

                }

            }

            editTextNum.apply {

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
                tvT1Score.text = it.teamOneScore.toString()
                tvT2Score.text = it.teamTwoScore.toString()
            }
        }
    }

    private fun changeOrAddPlayer(playerAndTeam: String) {
        findNavController().navigate(
            SinglesDetailFragmentDirections.actionSinglesDetailFragmentToSelectFromRosterFragment(
            )
        )
        setFragmentResultListener(SelectFromRosterFragment.REQUEST_KEY_PLAYER) { _, bundle ->
            val result = bundle.getString(SelectFromRosterFragment.BUNDLE_KEY_PLAYER)
            when (playerAndTeam) {
                "t1" -> matchDetailViewModel.match!!.value!!.teamOnePlayer1 = result!!
                "t2" -> matchDetailViewModel.match!!.value!!.teamTwoPlayer1 = result!!
            }
            matchDetailViewModel.updateMatch()
        }
    }


}
