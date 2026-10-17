package com.example.todoapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todoapp.data.TodoEntity
import com.example.todoapp.ui.viewmodel.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: TodoViewModel, onNavigateHistory: () -> Unit, onNavigateSettings: () -> Unit) {
    val todos by viewModel.activeTodos.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Elegance Tasks") },
                actions = {
                    IconButton(onClick = onNavigateHistory) {
                        Icon(Icons.Default.History, contentDescription = "History")
                    }
                    IconButton(onClick = onNavigateSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (todos.isEmpty()) {
                Text(
                    "No active tasks. Tap + to add one!",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(todos) { todo ->
                        TodoCard(todo, onToggle = { viewModel.toggleComplete(todo) })
                    }
                }
            }
        }

        if (showAddDialog) {
            AddTodoDialog(
                onDismiss = { showAddDialog = false },
                onSave = { title, desc, prio, cat ->
                    viewModel.addTodo(title, desc, System.currentTimeMillis() + 86400000, prio, cat)
                    showAddDialog = false
                }
            )
        }
    }
}

@Composable
fun TodoCard(todo: TodoEntity, onToggle: () -> Unit) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = todo.isCompleted, onCheckedChange = { onToggle() })
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = todo.title, style = MaterialTheme.typography.titleMedium)
                if (todo.description.isNotEmpty()) {
                    Text(text = todo.description, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Composable
fun AddTodoDialog(onDismiss: () -> Unit, onSave: (String, String, Int, String) -> Unit) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Task") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    shape = MaterialTheme.shapes.small
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    shape = MaterialTheme.shapes.small
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { if (title.isNotBlank()) onSave(title, description, 1, "Inbox") },
                shape = MaterialTheme.shapes.small
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
