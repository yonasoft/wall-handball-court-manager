package com.yonasoft.handballcourtmanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yonasoft.handballcourtmanager.R
import com.yonasoft.handballcourtmanager.databinding.PlayerQueueItemBinding
import com.yonasoft.handballcourtmanager.db.playersdb.Player

//Recycler view adapter for SelectFromRosterFragment that lists the roster
//The clicked item(a player) will be passed to a bundle in SelectFromRosterFragment
class PlayerSelectAdapter(private val queue: List<Player>) :
    RecyclerView.Adapter<PlayerSelectAdapter.ViewHolder>() {

    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position:Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class ViewHolder(val binding: PlayerQueueItemBinding, listener: OnItemClickListener) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
        fun bind(player: Player) {
            binding.playerName.text = player.name
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: PlayerQueueItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.player_queue_item, parent, false)
        return ViewHolder(binding,listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(queue[position])
    }

    override fun getItemCount(): Int {
        return queue.size
    }
}