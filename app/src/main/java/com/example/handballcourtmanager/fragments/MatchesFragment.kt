package com.example.handballcourtmanager.fragments

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.handballcourtmanager.R
import com.example.handballcourtmanager.adapter.ActiveMatchesAdapter
import com.example.handballcourtmanager.databinding.FragmentCurrentMatchesBinding
import com.example.handballcourtmanager.db.matchesdb.Doubles
import com.example.handballcourtmanager.db.matchesdb.Singles
import com.example.handballcourtmanager.db.matchesdb.Triangle
import com.example.handballcourtmanager.db.playersdb.Player


class MatchesFragment : Fragment() {

    private var matchesList = arrayListOf(Singles(Player(0,"Kevin",false),Player(0,"Kevin",false)),
        Doubles(arrayOf(Player(0,"Kevin",false),Player(0,"Win",false)),arrayOf(Player(0,"Kevin",false),Player(0,"Win",false))),
        Triangle(Player(0, "Kevin", false), Player(0, "Kevin", false), Player(0, "Kevin", false))
    )
    private var binding: FragmentCurrentMatchesBinding?=null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater,
            R.layout.fragment_current_matches,container,false)
        val view=binding!!.root

        setHasOptionsMenu(true)
        setupRecyclerView()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.fabAddMatches.setOnClickListener {

        }
    }

    private fun setupRecyclerView() {
        val adapter = ActiveMatchesAdapter(matchesList)
        val layoutManager = LinearLayoutManager(this.context)
        layoutManager.orientation = RecyclerView.VERTICAL
        binding!!.rcvActiveMatches.layoutManager = layoutManager
        binding!!.rcvActiveMatches.adapter = adapter
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.matches_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.help_item -> findNavController().navigate(
                R.id.action_matchesFragment_to_helpFragment
            )
        }
            return super.onOptionsItemSelected(item)
    }


}