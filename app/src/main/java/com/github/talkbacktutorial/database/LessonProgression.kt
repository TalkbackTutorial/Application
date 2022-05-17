package com.github.talkbacktutorial.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lesson_table")
data class LessonProgression(
    @PrimaryKey
    val id: Int,
    val completed: Boolean,
    val modulesCompleted: Int
)