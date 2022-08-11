package com.github.talkbacktutorial.database.gamemode

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * A data access object for accessing game mode rows
 * @author Antony Loose
 */
@Dao
interface GameModeDao {

    @Query("SELECT MAX(highScore) FROM gameMode_table")
    fun getHighScore(): LiveData<Int>

    @Query("SELECT highScore FROM gameMode_table ORDER BY highScore ASC")
    fun getAllHighScores(): LiveData<List<Int>>

    @Insert()
    suspend fun addHighScore(gameMode: GameMode)
}