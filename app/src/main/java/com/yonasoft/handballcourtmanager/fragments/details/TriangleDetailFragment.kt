package com.yonasoft.handballcourtmanager.fragments.details

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yonasoft.handballcourtmanager.R
import com.yonasoft.handballcourtmanager.databinding.FragmentTriangleDetailBinding
import com.yonasoft.handballcourtmanager.db.matchesdb.MatchType
import com.yonasoft.handballcourtmanager.fragments.details.dialogs.EndMatchDialogFragment

import com.yonasoft.handballcourtmanager.fragments.details.viewmodel.MatchDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TriangleDetailFragment : Fragment() {

    private var binding: FragmentTriangleDetailBinding?=null
    private val args: TriangleDetailFragmentArgs by navArgs()
    private val matchDetailViewModel by viewModels<MatchDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_triangle_detail, container, false)
        val view = binding!!.root

        binding!!.viewModel = matchDetailViewModel

        setupObservers()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
    }


    private fun setUpListeners() {

        binding!!.apply {
            tvT1.setOnClickListener { changeOrAddPlayer("t1") }
            tvT2.setOnClickListener { changeOrAddPlayer("t2") }
            tvT3.setOnClickListener { changeOrAddPlayer("t3") }

            // Setting click listeners for Add/Subtract buttons
            setOnClickListenerForPoints(btnT1Add, "t1", true)
            setOnClickListenerForPoints(btnT2Add, "t2", true)
            setOnClickListenerForPoints(btnT3Add, "t3", true)
            setOnClickListenerForPoints(btnT1Sub, "t1", false)
            setOnClickListenerForPoints(btnT2Sub, "t2", false)
            setOnClickListenerForPoints(btnT3Sub, "t3", false)

            btnEndMatch.setOnClickListener {
                val match = matchDetailViewModel.match.value!!
                //Makes sure all the textview for the players are filled in
                if (match.teams[1]!![0] != "TBA" && match.teams[2]!![0] != "TBA" && match.teams[3]!![0] != "TBA") {
                    //Navigate to dialog to return to winner's queue with an array of the players in this match, and the match type
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
                                        match.teams[1]!![0],
                                        match.teams[1]!![1],
                                        match.teams[2]!![0],
                                        match.teams[2]!![1],
                                        match.teams[3]!![0]
                                    ),
                                    MatchType.TRIANGLE.name
                                )
                            )
                            matchDetailViewModel.completeMatch()

                        }

                    }
                } else {
                    //Prompts user to add player if they haven't already
                    Toast.makeText(context, "Press the \"TBA\" to add a player!", Toast.LENGTH_LONG)
                        .show()
                }
            }
            //Code for interaction for editing court number
            binding!!.editTextNum.apply {

                setOnClickListener {
                    binding!!.editTextNum.text!!.clear()
                }
                //When you press the check button aka enter? on the on-screen keyboard it will set the new edited text as the court number
                setImeActionLabel(binding!!.editTextNum.text.toString(), KeyEvent.KEYCODE_ENTER)
                //Changes court number when out of focus
                setOnFocusChangeListener { _, _ ->
                    matchDetailViewModel.updateCourtNum(binding!!.editTextNum.text.toString())
                    isCursorVisible=false
                }

            }
        }
    }

    private fun setOnClickListenerForPoints(button: Button, team: String, isAddition: Boolean) {
        button.setOnClickListener {
            if (isAddition) matchDetailViewModel.addPoints(team)
            else matchDetailViewModel.deductPoints(team)
        }
    }
    //Observers for updated data to be reflected in the views
    private fun setupObservers() {
        binding!!.viewModel!!.match.observe(viewLifecycleOwner) {
            binding!!.apply {
                editTextNum.setText(it.courtNumber)
                tvT1.text = it.teams[1]!![0]
                tvT2.text = it.teams[2]!![0]
                tvT3.text = it.teams[3]!![0]
                tvT1Score.text = it.scores[1].toString()
                tvT2Score.text = it.scores[2].toString()
                tvT3Score.text = it.scores[3].toString()
            }
        }
    }
    //Code for changing and adding player
    //Parameter is what player and team will be changed in this match represented as a string
    private fun changeOrAddPlayer(playerAndTeam: String) {
        findNavController().navigate(
            TriangleDetailFragmentDirections.actionFragmentTriangleDetailToSelectFromRosterFragment()
        )
        //Gets the result of the player that will be added or changed to the match from the selection fragment that was opened
        setFragmentResultListener(SelectFromRosterFragment.REQUEST_KEY_PLAYER) { _, bundle ->
            val result = bundle.getString(SelectFromRosterFragment.BUNDLE_KEY_PLAYER)
            when (playerAndTeam) {
                "t1" -> matchDetailViewModel.match.value!!.teams[1]!![0]= result!!
                "t2" -> matchDetailViewModel.match.value!!.teams[2]!![0] = result!!
                "t3" -> matchDetailViewModel.match.value!!.teams[3]!![0] = result!!
            }
            //Updates the players in tha match through the view model into database
            matchDetailViewModel.updateMatch()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}