package com.example.handballcourtmanager.fragments

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.handballcourtmanager.R
import com.example.handballcourtmanager.adapter.ActiveMatchesAdapter
import com.example.handballcourtmanager.databinding.FragmentCurrentMatchesBinding
import com.example.handballcourtmanager.db.matchesdb.Match
import com.example.handballcourtmanager.viewmodel.MatchesViewModel
import com.google.android.material.snackbar.Snackbar


class MatchesFragment : Fragment() {

    private var binding: FragmentCurrentMatchesBinding?=null
    private val viewModel:MatchesViewModel by viewModels()

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
            findNavController().navigate(
                R.id.action_matchesFragment_to_createMatchDialogFragment
            )
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this.context)
        layoutManager.orientation = RecyclerView.VERTICAL
        binding!!.rcvActiveMatches.layoutManager = layoutManager
        viewModel.matchesList.observe(viewLifecycleOwner) {
            binding!!.rcvActiveMatches.adapter = ActiveMatchesAdapter(it)
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val removedMatch: Match = viewModel.matchesList.value!![viewHolder.adapterPosition]

                class PlayerDeletionCallback: Snackbar.Callback(){
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        super.onDismissed(transientBottomBar, event)
                        viewModel.removeMatch(removedMatch)
                    }

                }

                Snackbar.make(
                    binding!!.root,  "Match is being removed. press 'Undo' to stop!",
                    Snackbar.LENGTH_LONG).
                setAction(
                    "Undo"
                ) {

                }.addCallback(PlayerDeletionCallback())
                    .show()
            }
        }).attachToRecyclerView(binding!!.rcvActiveMatches)


    }

    //private fun addTestPlayer()


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.matches_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

            return super.onOptionsItemSelected(item)
    }


}