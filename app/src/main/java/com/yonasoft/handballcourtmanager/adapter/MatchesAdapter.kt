package com.yonasoft.handballcourtmanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.yonasoft.handballcourtmanager.R
import com.yonasoft.handballcourtmanager.databinding.DoublesMatchItemBinding
import com.yonasoft.handballcourtmanager.databinding.SinglesMatchItemBinding
import com.yonasoft.handballcourtmanager.databinding.TriangleMatchItemBinding
import com.yonasoft.handballcourtmanager.db.matchesdb.Match
import com.yonasoft.handballcourtmanager.db.matchesdb.MatchType
import com.yonasoft.handballcourtmanager.fragments.matches.MatchesFragmentDirections

class MatchesAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataList = emptyList<Match>()

    //Inflates view holder based the layout returned from getItemViewType()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bindingSingles = SinglesMatchItemBinding.inflate(inflater, parent, false)
        val bindingDoubles = DoublesMatchItemBinding.inflate(inflater, parent, false)
        val bindingTriangle = TriangleMatchItemBinding.inflate(inflater, parent, false)
        return when (viewType) {
            R.layout.singles_match_item -> SinglesMatchesHolder(bindingSingles)
            R.layout.doubles_match_item -> DoublesMatchesHolder(bindingDoubles)
            R.layout.triangle_match_item -> TriangleMatchesHolder(bindingTriangle)
            else -> throw RuntimeException("View holder type exception")
        }
    }

    //bind view holder based on layout given
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val match = dataList[position]
        when (holder.itemViewType) {
            R.layout.singles_match_item -> (holder as SinglesMatchesHolder).bind(match)
            R.layout.doubles_match_item -> (holder as DoublesMatchesHolder).bind(match)
            R.layout.triangle_match_item -> (holder as TriangleMatchesHolder).bind(match)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    //Return a layout type for the that specific match
    override fun getItemViewType(position: Int): Int {
        return when (dataList[position].matchType) {
            MatchType.SINGLES -> R.layout.singles_match_item
            MatchType.DOUBLES -> R.layout.doubles_match_item
            MatchType.TRIANGLE -> R.layout.triangle_match_item
        }
    }

    fun setData(data:List<Match>){
        this.dataList = data
    }

    //Match view holder for singles
    class SinglesMatchesHolder(
        val binding: SinglesMatchItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(match: Match) {
            binding.tvCourtNumber.text = match.courtNumber
            binding.teamOne.text = match.teams[1]!![0]
            binding.teamTwo.text = match.teams[2]!![0]
            binding.t1Score.text = match.scores[1].toString()
            binding.t2Score.text = match.scores[2].toString()
            //Will send to the detail screen of the match when clicked
            if (!match.isCompleted) {binding.root.setOnClickListener {

                binding.root.findNavController().navigate(
                    MatchesFragmentDirections.actionMatchesFragmentToSinglesDetailFragment(match.id)
                )
            }
            }
        }
    }

    //Match view holder for doubles
    class DoublesMatchesHolder(
        val binding: DoublesMatchItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(match: Match) {
            binding.tvCourtNumber.text = match.courtNumber
            binding.teamOneP1.text = match.teams[1]!![0]
            binding.teamOneP2.text = match.teams[1]!![1]
            binding.teamTwoP1.text = match.teams[2]!![0]
            binding.teamTwoP2.text = match.teams[2]!![1]
            binding.t1Score.text = match.scores[1].toString()
            binding.t2Score.text = match.scores[2].toString()
            //Will send to the detail screen of the match when clicked
            if (!match.isCompleted) {
                binding.root.setOnClickListener {
                    binding.root.findNavController().navigate(
                        MatchesFragmentDirections.actionMatchesFragmentToFragmentDoublesDetail(match.id)
                    )
                }
            }
        }
    }

    //Match view holder for triangle
    class TriangleMatchesHolder(
        val binding: TriangleMatchItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(match: Match) {
            binding.tvCourtNumber.text = match.courtNumber
            binding.teamOne.text = match.teams[1]!![0]
            binding.teamTwo.text = match.teams[2]!![0]
            binding.teamThree.text = match.teams[3]!![0]
            binding.t1Score.text = match.scores[1].toString()
            binding.t2Score.text = match.scores[2].toString()
            binding.t3Score.text = match.scores[3].toString()
            //Will send to the detail screen of the match when clicked
            if(!match.isCompleted) {
                binding.root.setOnClickListener {
                    binding.root.findNavController().navigate(
                        MatchesFragmentDirections.actionMatchesFragmentToFragmentTriangleDetail(match.id)
                    )
                }
            }
        }
    }

}
