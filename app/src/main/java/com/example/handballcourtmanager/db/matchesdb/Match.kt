package com.example.handballcourtmanager.db.matchesdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "matches")
data class Match(
    @PrimaryKey
    val id: UUID,
    @ColumnInfo(name="match_type")
    val matchType:String,
    @ColumnInfo(name="court_number")
    var courtNumber: String = "N/A",
    @ColumnInfo(name="t1_score")
    var teamOneScore: Int = 0,
    @ColumnInfo(name="t2_score")
    var teamTwoScore: Int = 0,
    @ColumnInfo(name="t3_score")
    var teamThreeScore: Int = 0,
    @ColumnInfo(name="t1_p1")
    var teamOnePlayer1: String=" ",
    @ColumnInfo(name="t1_p2")
    var teamOnePlayer2:String=" ",
    @ColumnInfo(name="t2_p1")
    var teamTwoPlayer1:String=" ",
    @ColumnInfo(name="t2_p2")
    var teamTwoPlayer2:String=" ",
    @ColumnInfo(name="t3_p1")
    var teamThreePlayer:String=" ",
    @ColumnInfo(name="is_completed")
    var isCompleted:Boolean=false,
    @ColumnInfo(name="winner")
    var winner1: String=" ",
    @ColumnInfo(name="winner2")
    var winner2: String=" ",


    )