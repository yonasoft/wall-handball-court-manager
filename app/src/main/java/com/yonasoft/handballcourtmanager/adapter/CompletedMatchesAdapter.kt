
package com.yonasoft.handballcourtmanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yonasoft.handballcourtmanager.R
import com.yonasoft.handballcourtmanager.databinding.DoublesMatchItemBinding
import com.yonasoft.handballcourtmanager.databinding.SinglesMatchItemBinding
import com.yonasoft.handballcourtmanager.databinding.TriangleMatchItemBinding
import com.yonasoft.handballcourtmanager.db.matchesdb.Match
import com.yonasoft.handballcourtmanager.db.matchesdb.MatchType


//Adapter for results. same as the active matches adapter EXCEPT there is no click listener
class CompletedMatchesAdapter(private val matches: List<Match>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

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
            R.layout.singles_match_item  -> (holder as SinglesMatchesHolder).bind(match)
            R.layout.doubles_match_item -> (holder as DoublesMatchesHolder).bind(match)
            R.layout.triangle_match_item -> (holder as TriangleMatchesHolder).bind(match)
        }

    }

    override fun getItemCount(): Int {
        return matches.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(matches[position].matchType){
            MatchType.SINGLES -> R.layout.singles_match_item
            MatchType.DOUBLES -> R.layout.doubles_match_item
            MatchType.TRIANGLE -> R.layout.triangle_match_item
        }
    }

    class SinglesMatchesHolder(
        val binding:SinglesMatchItemBinding
    ):RecyclerView.ViewHolder(binding.root){
        fun bind(match:Match){
            binding.tvCourtNumber.text = match.courtNumber
            binding.teamOne.text = match.teamOnePlayer1
            binding.teamTwo.text = match.teamTwoPlayer1
            binding.t1Score.text = match.teamOneScore.toString()
            binding.t2Score.text = match.teamTwoScore.toString()
        }
    }


    class DoublesMatchesHolder(
        val binding:DoublesMatchItemBinding
    ):RecyclerView.ViewHolder(binding.root){
        fun bind(match:Match){
            binding.tvCourtNumber.text = match.courtNumber
            binding.teamOneP1.text= match.teamOnePlayer1
            binding.teamOneP2.text= match.teamOnePlayer2
            binding.teamTwoP1.text= match.teamTwoPlayer1
            binding.teamTwoP2.text= match.teamTwoPlayer2
            binding.t1Score.text = match.teamOneScore.toString()
            binding.t2Score.text = match.teamTwoScore.toString()

        }
    }

    class TriangleMatchesHolder(
        val binding:TriangleMatchItemBinding
    ):RecyclerView.ViewHolder(binding.root){
        fun bind(match:Match){
            binding.tvCourtNumber.text = match.courtNumber
            binding.teamOne.text = match.teamOnePlayer1
            binding.teamTwo.text = match.teamTwoPlayer1
            binding.teamThree.text = match.teamThreePlayer
            binding.t1Score.text = match.teamOneScore.toString()
            binding.t2Score.text = match.teamTwoScore.toString()
            binding.t3Score.text = match.teamThreeScore.toString()


        }
    }

}
