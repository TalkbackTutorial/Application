package com.github.talkbacktutorial.database.gamemode

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Stores the high score, the gameMode_table will store all high scores the player has ever got,
 * this may be used in the future to show progression.
 * @author Antony Loose
 */
@Entity(tableName = "gameMode_table")
data class GameMode (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "highScore")
    val highScore: Int
)