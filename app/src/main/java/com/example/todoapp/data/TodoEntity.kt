package com.example.todoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val dueDate: Long,
    val priority: Int, // 1: Low, 2: Medium, 3: High
    val isCompleted: Boolean = false,
    val isArchived: Boolean = false,
    val category: String = "Inbox"
)
