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
        //Setup observers of the views
        setupObservers()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Sets up listeners
        setUpListeners()
    }


    private fun setUpListeners() {
        binding!!.apply {
            //Add or change players when click which open an add player dialog
            tvT1.setOnClickListener {
                changeOrAddPlayer("t1")
            }
            tvT2.setOnClickListener {
                changeOrAddPlayer("t2")
            }
            //Add or deduct points based on team
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
            //Ends match
            btnEndMatch.setOnClickListener {
                val match = matchDetailViewModel.match!!.value!!
                //Makes sure all the textview for the players are filled in
                if (match.teamOnePlayer1 != "TBA" && match.teamTwoPlayer1 != "TBA") {
                    findNavController().navigate(SinglesDetailFragmentDirections.actionSinglesDetailFragmentToEndMatchDialogFragment())
                    //Set listener to determine the result of the dialog.
                    setFragmentResultListener(EndMatchDialogFragment.REQUEST_KEY_END) { _, bundle ->
                        val result = bundle.getBoolean(EndMatchDialogFragment.BUNDLE_KEY_END)
                        //If the answer is true to the dialog this runs
                        if (result) {
                            //Pop back stack to this fragment
                            findNavController().popBackStack()
                            //Navigate to dialog to return to winner's queue with an array of the players in this match, and the match type
                            findNavController().navigate(
                                SinglesDetailFragmentDirections.actionSinglesDetailFragmentToReturnToWinnersDialogFragment(
                                    arrayOf(
                                        match.teamOnePlayer1,
                                        match.teamOnePlayer2,
                                        match.teamTwoPlayer1,
                                        match.teamTwoPlayer2,
                                        match.teamThreePlayer
                                    ),
                                    MatchTypes.SINGLES
                                )
                            )
                            //Completes match
                            matchDetailViewModel.completeMatch()
                        }
                    }

                } else {
                    Toast.makeText(context, "Press the \"TBA\" to add a player!", Toast.LENGTH_LONG)
                        .show()
                }
            }

            //Code for interaction for editing court number
            editTextNum.apply {
                setOnClickListener {
                    //Clears the court number for future editing
                    binding!!.editTextNum.text!!.clear()
                }
                //When you press the check button aka enter? on the on-screen keyboard it will set the new edited text as the court number
                setImeActionLabel(binding!!.editTextNum.text.toString(), KeyEvent.KEYCODE_ENTER)
                //Changes court number when out of focus
                setOnFocusChangeListener { _, _ ->
                    matchDetailViewModel.updateCourtNum(binding!!.editTextNum.text.toString())
                }
            }
        }
    }
    //Observers for updated data to be reflected in the views
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

    //Code for changing and adding player
    //Parameter is what player and team will be changed in this match represented as a string
    private fun changeOrAddPlayer(playerAndTeam: String) {
        findNavController().navigate(
            SinglesDetailFragmentDirections.actionSinglesDetailFragmentToSelectFromRosterFragment(
            )
        )
        //Gets the result of the player that will be added or changed to the match from the fragment screen that was opened
        setFragmentResultListener(SelectFromRosterFragment.REQUEST_KEY_PLAYER) { _, bundle ->
            val result = bundle.getString(SelectFromRosterFragment.BUNDLE_KEY_PLAYER)
            when (playerAndTeam) {
                "t1" -> matchDetailViewModel.match!!.value!!.teamOnePlayer1 = result!!
                "t2" -> matchDetailViewModel.match!!.value!!.teamTwoPlayer1 = result!!
            }
            //Updates the players in tha match through the view model into database
            matchDetailViewModel.updateMatch()
        }
    }


}
