package com.example.fitlifeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.fitlifeapp.screen.HomeScreen
import com.example.fitlifeapp.ui.theme.FitLifeTheme
import com.example.fitlifeapp.screen.EjerciciosScreen
import com.example.fitlifeapp.screen.RutinasScreen
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitLifeTheme {
                HomeScreen()
            }
        }
    }
}
