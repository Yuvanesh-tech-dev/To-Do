package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.ui.screens.HistoryScreen
import com.example.todoapp.ui.screens.MainScreen
import com.example.todoapp.ui.screens.SettingsScreen
import com.example.todoapp.ui.theme.TodoAppTheme
import com.example.todoapp.ui.viewmodel.TodoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoAppTheme {
                val navController = rememberNavController()
                val viewModel: TodoViewModel = viewModel()

                NavHost(navController = navController, startDestination = "main") {
                    composable("main") {
                        MainScreen(
                            viewModel = viewModel,
                            onNavigateHistory = { navController.navigate("history") },
                            onNavigateSettings = { navController.navigate("settings") }
                        )
                    }
                    composable("history") {
                        HistoryScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
                    }
                    composable("settings") {
                        SettingsScreen(onBack = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}
