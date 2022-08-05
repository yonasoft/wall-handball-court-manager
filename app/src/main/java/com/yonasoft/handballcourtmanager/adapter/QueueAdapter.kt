package com.yonasoft.handballcourtmanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yonasoft.handballcourtmanager.R
import com.yonasoft.handballcourtmanager.databinding.PlayerQueueItemBinding
import com.yonasoft.handballcourtmanager.db.playersdb.Player

//Adapter for players in queue
class QueueAdapter(private val queue: List<Player>):
RecyclerView.Adapter<QueueAdapter.ViewHolder>(){

    class ViewHolder(val binding:PlayerQueueItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(player: Player){
                binding.playerName.text = player.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater =   LayoutInflater.from(parent.context)
        val binding:PlayerQueueItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.player_queue_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(queue[position])
    }

    override fun getItemCount(): Int {
        return queue.size
    }
}