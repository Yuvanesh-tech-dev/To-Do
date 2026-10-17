package com.example.todoapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Todoist-Style Preferences", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))
            ListItem(
                headlineContent = { Text("Theme Options") },
                supportingContent = { Text("Customize dynamic color accents and dark mode") }
            )
            ListItem(
                headlineContent = { Text("Smart Notifications") },
                supportingContent = { Text("Configure daily summaries and due dates") }
            )
            ListItem(
                headlineContent = { Text("Widget Customization") },
                supportingContent = { Text("Select sticky-note themes and widget dimension sizes") }
            )
        }
    }
}
