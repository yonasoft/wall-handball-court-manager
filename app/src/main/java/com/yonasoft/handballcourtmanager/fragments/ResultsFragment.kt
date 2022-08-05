package com.yonasoft.handballcourtmanager.fragments

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yonasoft.handballcourtmanager.R
import com.yonasoft.handballcourtmanager.adapter.CompletedMatchesAdapter
import com.yonasoft.handballcourtmanager.databinding.FragmentResultsBinding
import com.yonasoft.handballcourtmanager.db.matchesdb.Match
import com.yonasoft.handballcourtmanager.viewmodel.MatchesViewModel
import com.google.android.material.snackbar.Snackbar

class ResultsFragment : Fragment() {


    private var binding: FragmentResultsBinding? = null
    private val viewModel: MatchesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_results, container, false)
        setHasOptionsMenu(true)
        setupRecyclerView()

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.results_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onDeleteMatches(item.itemId)
        return super.onOptionsItemSelected(item)
    }


    private fun onDeleteMatches(idOfQueueDeletion: Int) {
        val removedQueueText: String
        val removedList = mutableListOf<Match>()

        when (idOfQueueDeletion) {
            R.id.clear_results -> {
                removedList.addAll(viewModel.resultsList.value!!)
                removedQueueText = "Matches are being cleared!"
                viewModel.removeAllResults()
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

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this.context)
        layoutManager.orientation = RecyclerView.VERTICAL
        binding!!.rcvResults.layoutManager = layoutManager
        viewModel.resultsList.observe(viewLifecycleOwner) {
            binding!!.rcvResults.adapter = CompletedMatchesAdapter(it)
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
                val removedMatch: Match = viewModel.resultsList.value!![viewHolder.adapterPosition]
                viewModel.removeMatch(removedMatch)

                Snackbar.make(
                    binding!!.root, "Result is being removed. press 'Undo' to stop!",
                    Snackbar.LENGTH_LONG
                ).setAction(
                    "Undo"
                ) {
                    viewModel.addMatch(removedMatch)
                }.show()
            }
        }).attachToRecyclerView(binding!!.rcvResults)
    }

}