package com.yonasoft.handballcourtmanager.fragments.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yonasoft.handballcourtmanager.R
import com.yonasoft.handballcourtmanager.adapter.PlayerSelectAdapter
import com.yonasoft.handballcourtmanager.databinding.FragmentSelectFromRosterBinding
import com.yonasoft.handballcourtmanager.db.playersdb.Player
import com.yonasoft.handballcourtmanager.fragments.roster.viewmodel.RosterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectFromRosterFragment:Fragment() {

    private var binding:FragmentSelectFromRosterBinding?=null

    companion object{
        const val REQUEST_KEY_PLAYER ="request_key_player"
        const val BUNDLE_KEY_PLAYER = "bundle_key_player"
    }

    private val viewModel: RosterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_from_roster,container,false)
        val view = binding!!.root
        binding!!.viewModel = viewModel

        setupRecyclerView(binding!!.queueRcv, viewModel.regularQueue)
        setupRecyclerView(binding!!.winnersRcv,viewModel.winnerQueue)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnAdd?.setOnClickListener {
            viewModel.nameToAdd.value?.let { nameToAdd ->
                sendPlayerNameToPreviousFragment(nameToAdd)
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun setupRecyclerView(rcv: RecyclerView, queue: LiveData<List<Player>>) {
        rcv.layoutManager = LinearLayoutManager(this.context).apply {
            orientation = RecyclerView.VERTICAL
        }

        queue.observe(viewLifecycleOwner) { players ->
            val adapter = PlayerSelectAdapter(players)
            rcv.adapter = adapter

            adapter.setOnItemClickListener(object : PlayerSelectAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val playerToAdd = players[position]
                    sendPlayerNameToPreviousFragment(playerToAdd.name)
                    viewModel.deletePlayer(playerToAdd)
                    findNavController().navigateUp()
                }
            })
        }
    }

    private fun sendPlayerNameToPreviousFragment(playerName: String) {
        setFragmentResult(
            REQUEST_KEY_PLAYER,
            bundleOf(BUNDLE_KEY_PLAYER to playerName)
        )
    }

}