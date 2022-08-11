package com.github.talkbacktutorial.database.gamemode

import androidx.lifecycle.LiveData

/**
 * The repository used to manipulate game data within the database
 * @author Antony Loose
 */
class GameModeRepository(private val gameModeDao: GameModeDao) {

    val getAllHighScores: LiveData<List<Int>> = gameModeDao.getAllHighScores()

    /**
     * Gets the high score from the db
     * @author Antony Loose
     */
    fun getHighScore(): LiveData<Int>{
        return gameModeDao.getHighScore()
    }

    /**
     * Gets all the high scores (past high scores)
     * @author Antony Loose
     */
    suspend fun addHighScore(highScore: Int){
        gameModeDao.addHighScore(GameMode(highScore))
    }
}