package com.yonasoft.handballcourtmanager.db.matchesdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "matches")
data class Match(
    @PrimaryKey
    val id: UUID,
    //Type of match (See the enum "Match_Types").
    @ColumnInfo(name="match_type")
    val matchType:MatchType,
    @ColumnInfo(name="court_number")
    var courtNumber: String = "N/A",
    @ColumnInfo(name="t1_score")
    var teamOneScore: Int = 0,
    @ColumnInfo(name="t2_score")
    var teamTwoScore: Int = 0,
    @ColumnInfo(name="t3_score")
    var teamThreeScore: Int = 0,
    @ColumnInfo(name="t1_p1")
    var teamOnePlayer1: String ="TBA",
    @ColumnInfo(name="t1_p2")
    var teamOnePlayer2: String ="TBA",
    @ColumnInfo(name="t2_p1")
    var teamTwoPlayer1: String ="TBA",
    @ColumnInfo(name="t2_p2")
    var teamTwoPlayer2: String ="TBA",
    @ColumnInfo(name="t3_p1")
    var teamThreePlayer: String ="TBA",
    @ColumnInfo(name="is_completed")
    var isCompleted:Boolean=false,

    )