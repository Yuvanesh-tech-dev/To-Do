package com.example.todoapp

import android.app.Application
import com.example.todoapp.data.TodoDatabase

class TodoApplication : Application() {
    val database by lazy { TodoDatabase.getDatabase(this) }
}
