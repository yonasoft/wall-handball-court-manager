package com.yonasoft.handballcourtmanager.fragments.matches

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.yonasoft.handballcourtmanager.R
import com.yonasoft.handballcourtmanager.adapter.MatchesAdapter
import com.yonasoft.handballcourtmanager.databinding.FragmentCurrentMatchesBinding
import com.yonasoft.handballcourtmanager.fragments.matches.viewmodel.MatchesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchesFragment : Fragment() {

    private var binding: FragmentCurrentMatchesBinding? = null
    private val viewModel: MatchesViewModel by viewModels()
    private val adapter = MatchesAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_current_matches, container, false)
        setupRecyclerView()
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolBar()
        setupAddMatchesFAB()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun setupToolBar() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.matches_toolbar, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                handleMenuItemSelection(menuItem.itemId)
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupRecyclerView() {
        binding?.apply {
            rcvActiveMatches.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            viewModel.matchesList.observe(viewLifecycleOwner) { matches ->
                adapter.setData(matches)
                rcvActiveMatches.adapter = adapter
            }
            setupSwipeToDeleteFeature(rcvActiveMatches)
        }
    }

    private fun setupSwipeToDeleteFeature(recyclerView: RecyclerView) {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val removedMatch = viewModel.matchesList.value!![viewHolder.adapterPosition]
                viewModel.removeMatch(removedMatch)
                showUndoSnackbar("Match is being removed. press 'Undo' to stop!") { viewModel.addMatch(removedMatch) }
            }
        }).attachToRecyclerView(recyclerView)
    }

    private fun setupAddMatchesFAB() {
        binding?.fabAddMatches?.setOnClickListener {
            findNavController().navigate(MatchesFragmentDirections.actionMatchesFragmentToCreateMatchDialogFragment())
        }
    }

    private fun handleMenuItemSelection(itemId: Int) {
        if (itemId == R.id.clear_matches) {
            val removedList = viewModel.matchesList.value?.toMutableList() ?: mutableListOf()
            viewModel.removeAllCurrentMatches()
            showUndoSnackbar("Matches are being cleared!") { viewModel.addMatches(removedList) }
        }
    }

    private fun showUndoSnackbar(message: String, undoAction: () -> Unit) {
        Snackbar.make(binding!!.root, message, Snackbar.LENGTH_LONG)
            .setAction("Undo") { undoAction() }
            .show()
    }
}
