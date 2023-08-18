package com.yonasoft.handballcourtmanager.fragments.results

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yonasoft.handballcourtmanager.R
import com.yonasoft.handballcourtmanager.adapter.MatchesAdapter
import com.yonasoft.handballcourtmanager.databinding.FragmentResultsBinding
import com.yonasoft.handballcourtmanager.db.matchesdb.Match
import com.yonasoft.handballcourtmanager.fragments.matches.viewmodel.MatchesViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultsFragment : Fragment() {

    private var binding: FragmentResultsBinding? = null
    private val viewModel: MatchesViewModel by viewModels()
    private val adapter: MatchesAdapter by lazy { MatchesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_results, container, false)
        setupMenuProvider()
        setupRecyclerView()

        return binding?.root
    }

    private fun setupMenuProvider() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.results_toolbar, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                onDeleteMatches(menuItem.itemId)
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun onDeleteMatches(idOfQueueDeletion: Int) {
        val removedQueueText = when (idOfQueueDeletion) {
            R.id.clear_results -> {
                viewModel.removeAllResults()
                "Matches are being cleared!"
            }
            else -> return
        }

        Snackbar.make(binding?.root ?: return, removedQueueText, Snackbar.LENGTH_LONG).setAction(
            "Undo"
        ) {
            viewModel.resultsList.value?.let { viewModel.addMatches(it) }
        }.show()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this.context)
        layoutManager.orientation = RecyclerView.VERTICAL
        binding!!.rcvResults.layoutManager = layoutManager
        binding!!.rcvResults.setupSwipeToDelete()
        viewModel.resultsList.observe(viewLifecycleOwner) {
            adapter.setData(it)
            binding!!.rcvResults.adapter = adapter
        }

    }

    private fun RecyclerView.setupSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val removedMatch = viewModel.resultsList.value?.get(position) ?: return

                viewModel.removeMatch(removedMatch)
                Snackbar.make(
                    this@setupSwipeToDelete, "Result is being removed. press 'Undo' to stop!",
                    Snackbar.LENGTH_LONG
                ).setAction("Undo") {
                    viewModel.addMatch(removedMatch)
                }.show()
            }
        }).attachToRecyclerView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
