package com.yonasoft.handballcourtmanager.fragments

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yonasoft.handballcourtmanager.R
import com.yonasoft.handballcourtmanager.adapter.QueueAdapter
import com.yonasoft.handballcourtmanager.databinding.FragmentRosterBinding
import com.yonasoft.handballcourtmanager.db.playersdb.Player
import com.yonasoft.handballcourtmanager.viewmodel.RosterViewModel
import com.google.android.material.snackbar.Snackbar

//List of all the players in queue
class RosterFragment : Fragment() {

    lateinit var binding: FragmentRosterBinding
    private val viewModel: RosterViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_roster, container, false
        )
        val view = binding.root
        binding.viewModel = viewModel
        //Recycler view for regular queue
        setupRecyclerView(binding.queueRcv, viewModel.regularQueue)
        //Recycler view for winner's queue
        setupRecyclerView(binding.winnersRcv,viewModel.winnerQueue)

        val menuHost: MenuHost =requireActivity()
        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.roster_list_toolbar, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                onDeleteQueue(menuItem.itemId)
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED
        )

        // OnclickListener for opening dialog to add player to regular queue
        binding.fabAddPlayers.setOnClickListener {
            findNavController().navigate(
                R.id.action_rosterFragment_to_addPlayerDialogFragment
            )
        }

        return view
    }

    //Sets up recycler view based on the queue
    // winners queue -> players with isWinner set to true
    // regular queue -> players with isWinner set to false
    private fun setupRecyclerView(rcv: RecyclerView, queue: LiveData<List<Player>>) {

        val layoutManager = LinearLayoutManager(this.context)
        layoutManager.orientation = RecyclerView.VERTICAL
        rcv.layoutManager = layoutManager
        queue.observe(viewLifecycleOwner) {
            rcv.adapter = QueueAdapter(it)
        }

        //Swipe to delete feature
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val removedPlayer: Player = queue.value!![viewHolder.adapterPosition]
                viewModel.deletePlayer(removedPlayer)

                Snackbar.make(
                    binding.root,
                    removedPlayer.name + " is being removed. press 'Undo' to stop!",
                    Snackbar.LENGTH_LONG
                ).setAction(
                    "Undo"
                ) {
                    viewModel.addPlayer(removedPlayer)
                }
                    .show()
            }
        }).attachToRecyclerView(rcv)

    }

    //Deletes based on id passed
    private fun onDeleteQueue(idOfQueueDeletion: Int) {
        var removedQueueText = ""
        val removedList = mutableListOf<Player>()

        when (idOfQueueDeletion) {
            //deletes regular queue
            R.id.clear_regular -> {
                removedQueueText = "Regular queue is cleared!"
                viewModel.regularQueue.value?.let { removedList.addAll(it) }
                viewModel.deleteRegularPlayers()
            }
            //deletes winner queue
            R.id.clear_winners -> {
                removedQueueText = "Winner queue is cleared"
                viewModel.winnerQueue.value?.let { removedList.addAll(it) }
                viewModel.deleteWinnerPlayers()
            }
            //clears entire queue
            R.id.clear_all_queues -> {
                removedQueueText = "The entire queue is clear!"
                viewModel.regularQueue.value?.let { removedList.addAll(it) }
                viewModel.winnerQueue.value?.let { removedList.addAll(it) }
                viewModel.deleteAllPlayers()
            }
        }
        //Snack bar message when a queue is cleared
        val snackBar =
            Snackbar.make(binding.root, removedQueueText, Snackbar.LENGTH_LONG).setAction(
                "Undo"
            ) {
                viewModel.addAllPlayers(removedList)
            }
        snackBar.show()
    }
}