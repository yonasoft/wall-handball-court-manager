package com.yonasoft.handballcourtmanager.db.playersdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players_in_queue")
data class Player(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo(name = "player_name")
    var name:String,
    //If is winner then it be in the winner's
    @ColumnInfo(name = "is_winner")
    var isWinner:Boolean = false,
)