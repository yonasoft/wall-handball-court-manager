package com.yonasoft.handballcourtmanager.db.playersdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "players_in_queue")
data class Player(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "player_name")
    var name: String,
    // Indicates if the player is a winner
    @ColumnInfo(name = "is_winner")
    var isWinner: Boolean = false
)