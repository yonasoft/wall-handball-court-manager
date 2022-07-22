package com.example.handballcourtmanager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.handballcourtmanager.R
import com.example.handballcourtmanager.adapter.QueueAdapter
import com.example.handballcourtmanager.databinding.FragmentRosterBinding
import com.example.handballcourtmanager.db.Player
import com.example.handballcourtmanager.viewmodel.RosterViewModel


class RosterFragment : Fragment() {

    lateinit var binding:FragmentRosterBinding
    lateinit var winnerQueue:List<Player>
    private val viewModel: RosterViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

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
        queue.observe(viewLifecycleOwner, Observer {
            rcv.adapter = QueueAdapter(it)
        })

    }



}