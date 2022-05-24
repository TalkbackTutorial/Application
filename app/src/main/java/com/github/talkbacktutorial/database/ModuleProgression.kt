package com.github.talkbacktutorial.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

/**
 * Stores the lesson number (id+1), if the lesson has been completed, and the number of modules completed
 * within the lesson
 * @author Antony Loose
 */
@Entity(tableName = "module_table")
data class ModuleProgression(
    @PrimaryKey(autoGenerate = true)
    @NotNull
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "moduleName")
    val moduleName: String,
    @ColumnInfo(name = "completed")
    var completed: Boolean,
    @ColumnInfo(name = "lessonNum")
    val lessonNum: Int
)