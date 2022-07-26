package com.example.handballcourtmanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.handballcourtmanager.R
import com.example.handballcourtmanager.databinding.DoublesMatchItemBinding
import com.example.handballcourtmanager.databinding.SinglesMatchItemBinding
import com.example.handballcourtmanager.databinding.TriangleMatchItemBinding
import com.example.handballcourtmanager.db.matchesdb.Doubles
import com.example.handballcourtmanager.db.matchesdb.Match
import com.example.handballcourtmanager.db.matchesdb.Singles
import com.example.handballcourtmanager.db.matchesdb.Triangle

class SinglesMatchesHolder(
    val binding:SinglesMatchItemBinding
):RecyclerView.ViewHolder(binding.root){
    fun bind(match:Singles){
        binding.tvCourtNumber.text = match.courtNumber
        binding.teamOne.text = match.teamOne.name
        binding.teamTwo.text = match.teamTwo.name
        binding.t1Score.text = match.teamOneScore.toString()
        binding.t2Score.text = match.teamTwoSCore.toString()

    }
}


class DoublesMatchesHolder(
    val binding:DoublesMatchItemBinding
):RecyclerView.ViewHolder(binding.root){
    fun bind(match:Doubles){
        binding.tvCourtNumber.text = match.courtNumber
        binding.teamOneP1.text= match.teamOne[0]!!.name
        binding.teamOneP2.text= match.teamOne[1]!!.name
        binding.teamTwoP1.text= match.teamOne[0]!!.name
        binding.teamTwoP2.text= match.teamOne[1]!!.name
        binding.t1Score.text = match.teamOneScore.toString()
        binding.t2Score.text = match.teamTwoSCore.toString()

    }
}

class TriangleMatchesHolder(
    val binding:TriangleMatchItemBinding
):RecyclerView.ViewHolder(binding.root){
    fun bind(match:Triangle){
        binding.tvCourtNumber.text = match.courtNumber
        binding.teamOne.text = match.teamOne.name
        binding.teamTwo.text = match.teamTwo.name
        binding.teamThree.text = match.teamThree.name
        binding.t1Score.text = match.teamOneScore.toString()
        binding.t2Score.text = match.teamTwoSCore.toString()
        binding.t3Score.text = match.teamThreeScore.toString()

    }
}

class ActiveMatchesAdapter(private val matches:List<Match>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bindingSingles =  SinglesMatchItemBinding.inflate(inflater,parent,false)
        val bindingDoubles =  DoublesMatchItemBinding.inflate(inflater,parent,false)
        val bindingTriangle =  TriangleMatchItemBinding.inflate(inflater,parent,false)
        return when (viewType) {
            R.layout.singles_match_item -> SinglesMatchesHolder(bindingSingles)
            R.layout.doubles_match_item -> DoublesMatchesHolder(bindingDoubles)
            R.layout.triangle_match_item -> TriangleMatchesHolder(bindingTriangle)
            else -> throw RuntimeException("View holder type exception")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val match  = matches[position]
        when(holder.itemViewType){
            R.layout.singles_match_item  -> (holder as SinglesMatchesHolder).bind(match as Singles)
            R.layout.doubles_match_item -> (holder as DoublesMatchesHolder).bind(match as Doubles)
            R.layout.triangle_match_item -> (holder as TriangleMatchesHolder).bind(match as Triangle)
        }
    }

    override fun getItemCount(): Int {
        return matches.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(matches[position]){
            is Singles -> R.layout.singles_match_item
            is Doubles -> R.layout.doubles_match_item
            is Triangle -> R.layout.triangle_match_item
            else -> throw RuntimeException("Not an excepted type")
        }
    }

}

