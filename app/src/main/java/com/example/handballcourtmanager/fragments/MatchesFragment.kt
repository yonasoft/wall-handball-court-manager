package com.example.handballcourtmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.handballcourtmanager.R
import com.example.handballcourtmanager.adapter.ActiveMatchesAdapter
import com.example.handballcourtmanager.databinding.FragmentCurrentMatchesBinding
import com.example.handballcourtmanager.model.Match




class MatchesFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var matchesList =ArrayList<Match>(arrayListOf(Match(courtNum = 1, team1Name = "Kevin", team2Name = "Win", t1Score = 20, t2Score = 0 ),
    Match(courtNum = 2, team1Name = "David", team2Name = "Winston", t1Score = 20, t2Score = 0 )))
    private lateinit var binding:FragmentCurrentMatchesBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater,
            R.layout.fragment_current_matches,container,false)
        val view=binding.root


        setupRecyclerView()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabAddMatches.setOnClickListener {

        }
    }

    private fun setupRecyclerView() {

        val adapter = ActiveMatchesAdapter(matchesList!!)
        val layoutManager = LinearLayoutManager(this.context)
        layoutManager.orientation = RecyclerView.VERTICAL
        binding.rcvActiveMatches.layoutManager = layoutManager
        binding.rcvActiveMatches.adapter = adapter
    }

}