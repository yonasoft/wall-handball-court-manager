package com.example.handballcourtmanager.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players_in_queue")
data class Player(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo(name = "player_name")
    var name:String,
    @ColumnInfo(name = "is_winner")
    var isWinner:Boolean = false
)