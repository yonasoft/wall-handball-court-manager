package com.example.handballcourtmanager.fragments

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
import com.example.handballcourtmanager.R
import com.example.handballcourtmanager.adapter.PlayerSelectAdapter
import com.example.handballcourtmanager.databinding.FragmentSelectFromRosterBinding
import com.example.handballcourtmanager.db.playersdb.Player
import com.example.handballcourtmanager.viewmodel.RosterSelectViewModel
import com.example.handballcourtmanager.viewmodel.RosterSelectViewModelFactory


class SelectFromRosterFragment:Fragment() {

    var binding:FragmentSelectFromRosterBinding?=null

    companion object{
        const val REQUEST_KEY_PLAYER ="request_key_player"
        const val BUNDLE_KEY_PLAYER = "bundle_key_player"
    }

    private val viewModel:RosterSelectViewModel by viewModels{
        RosterSelectViewModelFactory()
    }

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

        binding!!.btnAdd.setOnClickListener{
            val nameToAdd = viewModel.nameToAdd
            setFragmentResult(REQUEST_KEY_PLAYER,
            bundleOf(BUNDLE_KEY_PLAYER to nameToAdd.value),
            )
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView(rcv: RecyclerView, queue: LiveData<List<Player>>) {

        val layoutManager = LinearLayoutManager(this.context)
        layoutManager.orientation = RecyclerView.VERTICAL
        rcv.layoutManager = layoutManager
        queue.observe(viewLifecycleOwner) {
            val adapter = PlayerSelectAdapter(it)
            rcv.adapter = adapter
            adapter.setOnItemClickListener(object : PlayerSelectAdapter.OnItemClickListener{
                override fun onItemClick(position: Int) {
                    val playerToAdd = queue.value!![position]
                    setFragmentResult(REQUEST_KEY_PLAYER,
                        bundleOf(BUNDLE_KEY_PLAYER to playerToAdd.name),
                    )
                    viewModel.deletePlayer(playerToAdd)
                    findNavController().navigateUp()
                }

            })

        }


    }

}