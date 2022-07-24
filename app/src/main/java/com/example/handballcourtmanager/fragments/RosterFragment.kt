package com.example.handballcourtmanager.fragments

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.handballcourtmanager.R
import com.example.handballcourtmanager.adapter.QueueAdapter
import com.example.handballcourtmanager.databinding.FragmentRosterBinding
import com.example.handballcourtmanager.db.playersdb.Player
import com.example.handballcourtmanager.viewmodel.RosterViewModel
import com.google.android.material.snackbar.Snackbar


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

        setHasOptionsMenu(true)

        binding.viewModel = viewModel
        setupRecyclerView(binding.queueRcv, viewModel.regularQueue)
        //setupRecyclerView(binding.winnersRcv,viewModel!!.ge)

        binding.fabAddPlayers.setOnClickListener {
            findNavController().navigate(
                R.id.action_rosterFragment_to_addPlayerDialogFragment
            )
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.roster_list_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.help_item-> findNavController().navigate(
                R.id.action_rosterFragment_to_helpFragment
            )
            else->onDeleteQueue(item.itemId)
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setupRecyclerView(rcv:RecyclerView, queue: LiveData<List<Player>>) {

        val layoutManager = LinearLayoutManager(this.context)
        layoutManager.orientation = RecyclerView.VERTICAL
        rcv.layoutManager = layoutManager
        queue.observe(viewLifecycleOwner) {
            rcv.adapter = QueueAdapter(it)
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
                val removedPlayer: Player = queue.value!![viewHolder.adapterPosition]
                removedPlayer.isDeleted=true
                viewModel.updatePlayer(removedPlayer)

                class PlayerDeletionCallback:Snackbar.Callback(){
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        super.onDismissed(transientBottomBar, event)
                        if(removedPlayer.isDeleted) {
                            viewModel.deletePlayer(removedPlayer)
                        }
                    }

                }

                Snackbar.make(binding.root, removedPlayer.name + " is being removed. press 'Undo' to stop!",Snackbar.LENGTH_LONG).
                setAction(
                    "Undo"
                ) {
                    removedPlayer.isDeleted = false
                    viewModel.updatePlayer(removedPlayer)
                }.addCallback(PlayerDeletionCallback())
                    .show()
            }
        }).attachToRecyclerView(rcv)

    }

    private fun onDeleteQueue(idOfQueueDeletion:Int){
        val removedQueueText: String
        val removedList = mutableListOf<Player>()

        when (idOfQueueDeletion) {
            R.id.clear_regular -> {
                removedQueueText = "Regular queue is cleared!"
                viewModel.regularQueue.value?.let { removedList.addAll(it) }
                viewModel.deleteRegularPlayers()
            }
            R.id.clear_winners -> {
                removedQueueText = "Winner queue is cleared"
                viewModel.winnerQueue.value?.let { removedList.addAll(it) }
                viewModel.deleteWinnerPlayers()
            }
            else -> {
                removedQueueText = "The entire queue is clear!"
                viewModel.regularQueue.value?.let { removedList.addAll(it) }
                viewModel.winnerQueue.value?.let { removedList.addAll(it) }
                viewModel.deleteAllPlayers()
            }
        }

        val snackBar = Snackbar.make(binding.root, removedQueueText, Snackbar.LENGTH_LONG).
        setAction(
            "Undo"
        ) {
            viewModel.addAllPlayers(removedList)
        }
        snackBar.show()


    }


}