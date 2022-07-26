package com.example.handballcourtmanager.db.matchesdb

import com.example.handballcourtmanager.db.playersdb.Player

class Triangle(
    val teamOne: Player,
    val teamTwo:Player,
    val teamThree:Player,
    var teamThreeScore:Int=0
    ):Match()