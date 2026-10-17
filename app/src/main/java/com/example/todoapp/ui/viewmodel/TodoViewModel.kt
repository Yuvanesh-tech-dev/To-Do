package com.example.todoapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.TodoApplication
import com.example.todoapp.data.TodoEntity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = (application as TodoApplication).database.todoDao()

    val activeTodos: StateFlow<List<TodoEntity>> = dao.getActiveTodos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val historyTodos: StateFlow<List<TodoEntity>> = dao.getHistoryTodos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addTodo(title: String, description: String, dueDate: Long, priority: Int, category: String) {
        viewModelScope.launch {
            dao.insertTodo(
                TodoEntity(
                    title = title,
                    description = description,
                    dueDate = dueDate,
                    priority = priority,
                    category = category
                )
            )
        }
    }

    fun toggleComplete(todo: TodoEntity) {
        viewModelScope.launch {
            dao.updateTodo(todo.copy(isCompleted = !todo.isCompleted, isArchived = !todo.isCompleted))
        }
    }

    fun deleteTodo(todo: TodoEntity) {
        viewModelScope.launch {
            dao.deleteTodo(todo)
        }
    }
}
