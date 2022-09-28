package com.yonasoft.handballcourtmanager.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yonasoft.handballcourtmanager.db.matchesdb.MatchType
import com.yonasoft.handballcourtmanager.db.playersdb.Player
import java.util.*

class Converter {

    @TypeConverter
    fun fromMatchType(matchType: MatchType): String{
        return matchType.name
    }

    @TypeConverter
    fun toMatchType(matchType: String): MatchType {
        return MatchType.valueOf(matchType)
    }

    @TypeConverter
    fun fromScores(scores:HashMap<Int,Int>): String{
        return Gson().toJson(scores)
    }

    @TypeConverter
    fun toScores(scores: String): HashMap<Int,Int> {
        return Gson().fromJson(scores,  object : TypeToken<HashMap<Int, Int>>() {}.type)
    }

    @TypeConverter
    fun fromTeams(teams:HashMap<Int,Array<String>>): String{
        return Gson().toJson(teams)
    }

    @TypeConverter
    fun toTeams(teams: String): HashMap<Int,Array<String>> {
        return Gson().fromJson(teams,  object : TypeToken<HashMap<Int,Array<String>>>() {}.type)
    }

}