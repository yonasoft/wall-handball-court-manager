package com.yonasoft.handballcourtmanager.fragments

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
import com.yonasoft.handballcourtmanager.db.matchesdb.Match
import com.yonasoft.handballcourtmanager.viewmodel.MatchesViewModel


//Matches fragment which list all current matches
class MatchesFragment : Fragment(){

    private var binding: FragmentCurrentMatchesBinding? = null
    private val viewModel: MatchesViewModel by viewModels()
    private val adapter:MatchesAdapter by lazy {MatchesAdapter()}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_current_matches, container, false
        )
        val view = binding!!.root

        //Recycler view for all the matches
        setupRecyclerView()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolBar()

        //click listener for  for FAB
        binding!!.fabAddMatches.setOnClickListener {
            findNavController().navigate(
                MatchesFragmentDirections.actionMatchesFragmentToCreateMatchDialogFragment()
            )
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun setupToolBar() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.matches_toolbar, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    onDeleteMatches(menuItem.itemId)
                    return true
                }
            }, viewLifecycleOwner, Lifecycle.State.RESUMED
        )
    }

    //setup recycler view oif matches
    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this.context)
        layoutManager.orientation = RecyclerView.VERTICAL
        binding!!.rcvActiveMatches.layoutManager = layoutManager
        //Observer for match data
        viewModel.matchesList.observe(viewLifecycleOwner) {
            adapter.setData(it)
            binding!!.rcvActiveMatches.adapter = adapter
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

    //Deletes the match if it the menu is a specific id
    private fun onDeleteMatches(idOfQueueDeletion: Int) {

        val removedQueueText: String
        val removedList = mutableListOf<Match>()

        //Delete based on menu ID
        when (idOfQueueDeletion) {
            R.id.clear_matches -> {
                removedList.addAll(viewModel.matchesList.value!!)
                removedQueueText = "Matches are being cleared!"
                viewModel.removeAllCurrentMatches()
            }
            else -> return
        }

        //Show snack bar and undo button
        val snackBar =
            Snackbar.make(binding!!.root, removedQueueText, Snackbar.LENGTH_LONG).setAction(
                "Undo"
            ) {
                viewModel.addMatches(removedList)
            }
        snackBar.show()
    }



}