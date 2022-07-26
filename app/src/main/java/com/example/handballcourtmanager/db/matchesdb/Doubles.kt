package com.example.handballcourtmanager.db.matchesdb

import com.example.handballcourtmanager.db.playersdb.Player

class Doubles(
    var teamOne: Array<Player?> = arrayOfNulls(2),
    var teamTwo: Array<Player?> = arrayOfNulls(2),

) : Match()