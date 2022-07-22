package com.example.handballcourtmanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.handballcourtmanager.R
import com.example.handballcourtmanager.model.Match

class ActiveMatchesAdapter(matchesList:ArrayList<Match>):
    RecyclerView.Adapter<ActiveMatchesAdapter.ViewHolder>() {
    var matchesList:ArrayList<Match> = matchesList



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTeam1:TextView = itemView.findViewById(R.id.team_one)
        val tvTeam2:TextView = itemView.findViewById(R.id.team_two)
        val tvT1Score:TextView = itemView.findViewById(R.id.t1_score)
        val tv2Score:TextView = itemView.findViewById(R.id.t2_score)
        val tvCourtNum:TextView = itemView.findViewById(R.id.tv_court_number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.active_match_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val match = matchesList[position]
        val courtNum = match.courtNum.toString()
        val teamOneName = match.team1Name
        val teamTwoName = match.team2Name
        val t1Score = match.t1Score.toString()
        val t2Score = match.t2Score.toString()

        holder.tvTeam1.text = teamOneName
        holder.tvTeam2.text = teamTwoName
        holder.tvT1Score.text = t1Score
        holder.tv2Score.text = t2Score
        holder.tvCourtNum.text = "#${courtNum}"
    }

    override fun getItemCount(): Int {
        return matchesList.size

    }


}