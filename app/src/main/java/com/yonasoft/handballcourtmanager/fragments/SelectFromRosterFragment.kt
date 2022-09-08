package com.yonasoft.handballcourtmanager.fragments

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
import com.yonasoft.handballcourtmanager.viewmodel.RosterViewModel

//Fragment when selecting player to add the match
class SelectFromRosterFragment:Fragment() {

    private var binding:FragmentSelectFromRosterBinding?=null

    companion object{
        //Player info to send back the detail fragment
        const val REQUEST_KEY_PLAYER ="request_key_player"
        const val BUNDLE_KEY_PLAYER = "bundle_key_player"
    }

    private val viewModel:RosterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_from_roster,container,false)
        val view = binding!!.root
        binding!!.viewModel = viewModel
        //Regular queue that is displayed
        setupRecyclerView(binding!!.queueRcv, viewModel.regularQueue)
        //Winners queue that is displayed
        setupRecyclerView(binding!!.winnersRcv,viewModel.winnerQueue)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Add player by name by pressing add and it will add text input as the player
        binding!!.btnAdd.setOnClickListener{
            //Sends player name back to the previous fragment
            val nameToAdd = viewModel.nameToAdd
            setFragmentResult(REQUEST_KEY_PLAYER,
            bundleOf(BUNDLE_KEY_PLAYER to nameToAdd.value),
            )
            //navigates back to previous fragment
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView(rcv: RecyclerView, queue: LiveData<List<Player>>) {

        //Sets up recycler view of the queue
        val layoutManager = LinearLayoutManager(this.context)
        layoutManager.orientation = RecyclerView.VERTICAL
        rcv.layoutManager = layoutManager
        queue.observe(viewLifecycleOwner) {
            val adapter = PlayerSelectAdapter(it)
            rcv.adapter = adapter
            //Clicking the player will send the player name to previous fragment and sets the name to the player clicked
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

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}