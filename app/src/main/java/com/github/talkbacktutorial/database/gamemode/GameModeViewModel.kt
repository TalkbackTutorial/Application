package com.github.talkbacktutorial.database.gamemode

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.github.talkbacktutorial.database.TMTDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * This view model acts as a communication centre between the repository and the UI
 * @param application the application context for the view model
 * @author Antony Loose
 */
class GameModeViewModel(application: Application): AndroidViewModel(application) {

    val getAllHighScores: LiveData<List<Int>>
    private val repository: GameModeRepository

    init {
        val gameModeDao = TMTDatabase.getDatabase(application).gameModeDao()
        repository = GameModeRepository(gameModeDao)
        getAllHighScores = repository.getAllHighScores
    }

    /**
     * Get the high score
     * @author Antony Loose
     */
    fun getHighScore(): LiveData<Int>{
        return repository.getHighScore()
    }

    /**
     * A function for adding new high scores
     * @author Antony Loose
     */
    fun addHighScore(highScore: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addHighScore(highScore)
        }
    }

    /**
     * Fill the database with a single high score of 0
     * @author Antony Loose
     */
    fun fillDatabase() {
        viewModelScope.launch(Dispatchers.IO){
            repository.addHighScore(0)
        }
    }

    /**
     * Wipe the database
     * @author Antony Loose
     */
    fun clearDatabase(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.wipeTable()
        }
    }
}