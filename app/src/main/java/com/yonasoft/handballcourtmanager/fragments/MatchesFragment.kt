package com.yonasoft.handballcourtmanager.fragments

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yonasoft.handballcourtmanager.R
import com.yonasoft.handballcourtmanager.adapter.ActiveMatchesAdapter
import com.yonasoft.handballcourtmanager.databinding.FragmentCurrentMatchesBinding
import com.yonasoft.handballcourtmanager.db.matchesdb.Match
import com.yonasoft.handballcourtmanager.viewmodel.MatchesViewModel
import com.google.android.material.snackbar.Snackbar

//Matches fragment which list all current matches
class MatchesFragment : Fragment() {

    private var binding: FragmentCurrentMatchesBinding? = null
    private val viewModel: MatchesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_current_matches, container, false
        )
        val view = binding!!.root

        setHasOptionsMenu(true)
        //Recycler view for all the matches
        setupRecyclerView()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.fabAddMatches.setOnClickListener {
            findNavController().navigate(
                MatchesFragmentDirections.actionMatchesFragmentToCreateMatchDialogFragment()
            )
        }

    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this.context)
        layoutManager.orientation = RecyclerView.VERTICAL
        binding!!.rcvActiveMatches.layoutManager = layoutManager
        //Observer for match data
        viewModel.matchesList.observe(viewLifecycleOwner) {
            binding!!.rcvActiveMatches.adapter = ActiveMatchesAdapter(it)

        }
        //Below is the swipe to delete feature
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
                viewModel.removeMatch(removedMatch)

                //Option to undo delete
                Snackbar.make(
                    binding!!.root, "Match is being removed. press 'Undo' to stop!",
                    Snackbar.LENGTH_LONG
                ).setAction(
                    "Undo"
                ) {
                    viewModel.addMatch(removedMatch)
                }.show()
            }
        }).attachToRecyclerView(binding!!.rcvActiveMatches)


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.matches_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onDeleteMatches(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    //Deletes the match if it the menu is a specific id
    private fun onDeleteMatches(idOfQueueDeletion: Int) {
        val removedQueueText: String
        val removedList = mutableListOf<Match>()

        when (idOfQueueDeletion) {
            R.id.clear_matches -> {
                removedList.addAll(viewModel.matchesList.value!!)
                removedQueueText = "Matches are being cleared!"
                viewModel.removeAllCurrentMatches()
            }
            else-> return

        }

        val snackBar =
            Snackbar.make(binding!!.root, removedQueueText, Snackbar.LENGTH_LONG).setAction(
                "Undo"
            ) {
                viewModel.addMatches(removedList)
            }
        snackBar.show()


    }


}