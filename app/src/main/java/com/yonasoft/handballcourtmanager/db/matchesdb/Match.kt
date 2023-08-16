package com.yonasoft.handballcourtmanager.db.matchesdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import kotlin.collections.HashMap

@Entity(tableName = "matches")
data class Match(
    @PrimaryKey
    val id: UUID,
    //Type of match (See the enum "Match_Types").
    @ColumnInfo(name="match_type")
    val matchType:MatchType,
    @ColumnInfo(name="court_number")
    var courtNumber: String = "N/A",
    @ColumnInfo(name="scores")
    var scores:HashMap<Int,Int> = hashMapOf(
        1 to 0,
        2 to 0,
        3 to 0),
    @ColumnInfo(name="players")
    var teams:HashMap<Int,Array<String>> = hashMapOf(
        1 to arrayOf("TBA","TBA"),
        2 to arrayOf("TBA","TBA"),
        3 to arrayOf("TBA")),
    @ColumnInfo(name="is_completed")
    var isCompleted:Boolean=false,

    )