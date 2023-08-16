package com.yonasoft.handballcourtmanager.fragments.roster

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
import com.yonasoft.handballcourtmanager.fragments.roster.viewmodel.RosterViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RosterFragment : Fragment() {

    private var binding: FragmentRosterBinding? = null
    private val viewModel: RosterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupDataBinding(inflater, container)
        setupRecyclerViews()
        setupMenuHost()
        setupFabAddPlayersClickListener()

        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun setupDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_roster, container, false)
        binding?.viewModel = viewModel
    }

    private fun setupRecyclerViews() {
        setupRecyclerView(binding?.queueRcv, viewModel.regularQueue)
        setupRecyclerView(binding?.winnersRcv, viewModel.winnerQueue)
    }

    private fun setupRecyclerView(rcv: RecyclerView?, queue: LiveData<List<Player>>) {
        val layoutManager = LinearLayoutManager(context).apply { orientation = RecyclerView.VERTICAL }
        rcv?.layoutManager = layoutManager
        queue.observe(viewLifecycleOwner) { players -> rcv?.adapter = QueueAdapter(players) }
        setupSwipeToDelete(rcv, queue)
    }

    private fun setupSwipeToDelete(rcv: RecyclerView?, queue: LiveData<List<Player>>) {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val removedPlayer = queue.value?.get(viewHolder.adapterPosition) ?: return
                viewModel.deletePlayer(removedPlayer)
                showSnackbar("${removedPlayer.name} is being removed. press 'Undo' to stop!") {
                    viewModel.addPlayer(removedPlayer)
                }
            }
        }).attachToRecyclerView(rcv)
    }

    private fun setupMenuHost() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(createMenuProvider(), viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun createMenuProvider() = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.roster_list_toolbar, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            onDeleteQueue(menuItem.itemId)
            return true
        }
    }

    private fun setupFabAddPlayersClickListener() {
        binding?.fabAddPlayers?.setOnClickListener {
            findNavController().navigate(R.id.action_rosterFragment_to_addPlayerDialogFragment)
        }
    }

    private fun onDeleteQueue(idOfQueueDeletion: Int) {
        val removedList = mutableListOf<Player>()
        val removedQueueText = when (idOfQueueDeletion) {
            R.id.clear_regular -> {
                viewModel.regularQueue.value?.let { removedList.addAll(it) }
                viewModel.deleteRegularPlayers()
                "Regular queue is cleared!"
            }
            R.id.clear_winners -> {
                viewModel.winnerQueue.value?.let { removedList.addAll(it) }
                viewModel.deleteWinnerPlayers()
                "Winner queue is cleared"
            }
            R.id.clear_all_queues -> {
                viewModel.regularQueue.value?.let { removedList.addAll(it) }
                viewModel.winnerQueue.value?.let { removedList.addAll(it) }
                viewModel.deleteAllPlayers()
                "The entire queue is clear!"
            }
            else -> return
        }
        showSnackbar(removedQueueText) {
            viewModel.addAllPlayers(removedList)
        }
    }

    private fun showSnackbar(message: String, action: (() -> Unit)? = null) {
        Snackbar.make(binding?.root ?: return, message, Snackbar.LENGTH_LONG).apply {
            action?.let {
                setAction("Undo", { it() })
            }
            show()
        }
    }

}
