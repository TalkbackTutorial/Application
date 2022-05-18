package com.github.talkbacktutorial.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "lesson_table")
data class LessonProgression(
    @PrimaryKey(autoGenerate = true)
    @NotNull
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "completed")
    val completed: Boolean,
    @ColumnInfo(name = "modulesCompleted")
    val modulesCompleted: Int
)