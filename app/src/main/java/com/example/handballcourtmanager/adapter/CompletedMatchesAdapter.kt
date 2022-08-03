
package com.example.handballcourtmanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.handballcourtmanager.R
import com.example.handballcourtmanager.databinding.DoublesMatchItemBinding
import com.example.handballcourtmanager.databinding.SinglesMatchItemBinding
import com.example.handballcourtmanager.databinding.TriangleMatchItemBinding
import com.example.handballcourtmanager.db.matchesdb.Match
import com.example.handballcourtmanager.db.matchesdb.MatchTypes
import com.example.handballcourtmanager.fragments.MatchesFragmentDirections



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
            MatchTypes.SINGLES -> R.layout.singles_match_item
            MatchTypes.DOUBLES -> R.layout.doubles_match_item
            MatchTypes.TRIANGLE -> R.layout.triangle_match_item
            else -> throw RuntimeException("Not an excepted type")
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
