package com.example.handballcourtmanager.db.matchesdb

import com.example.handballcourtmanager.db.playersdb.Player


open class Match(
    var courtNumber: String = "N/A",
    var teamOneScore: Int = 0,
    var teamTwoSCore: Int = 0,
    var isCompleted:Boolean=false,
    var winner: Player?=null
) {

}