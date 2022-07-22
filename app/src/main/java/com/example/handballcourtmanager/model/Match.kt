package com.example.handballcourtmanager.model

import com.example.handballcourtmanager.constants.MatchType

data class Match(
    var courtNum: Int = 0,
    var team1Name: String = "",
    var team2Name: String = "",
    var t1Score: Int = 0,
    var t2Score: Int = 0,
    var t1Aces: Int = 0,
    var t2Aces: Int = 0,
    var t1Kills: Int = 0,
    var t2Kills: Int = 0,
    var matchType: String = MatchType.SINGLES,
    var currentServer: String = team1Name,
    var winner: String = "",
    var isCompleted:Boolean=false,
)