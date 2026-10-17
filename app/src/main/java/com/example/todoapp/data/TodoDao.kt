package com.example.todoapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos WHERE isArchived = 0 ORDER BY dueDate ASC")
    fun getActiveTodos(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todos WHERE isArchived = 1 ORDER BY dueDate DESC")
    fun getHistoryTodos(): Flow<List<TodoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoEntity)

    @Update
    suspend fun updateTodo(todo: TodoEntity)

    @Delete
    suspend fun deleteTodo(todo: TodoEntity)
}
