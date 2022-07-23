package com.example.handballcourtmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.handballcourtmanager.db.Player
import com.example.handballcourtmanager.viewmodel.RosterViewModel
import com.google.android.material.snackbar.Snackbar


class RosterFragment : Fragment() {

    lateinit var binding:FragmentRosterBinding
    private val viewModel: RosterViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(layoutInflater,
            R.layout.fragment_roster,container,false)
        val view=binding.root

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
                val removedPlayer:Player = queue.value!![viewHolder.adapterPosition]
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


}