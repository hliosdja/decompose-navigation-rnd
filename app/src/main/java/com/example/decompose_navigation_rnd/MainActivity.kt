package com.example.decompose_navigation_rnd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import com.example.decompose_navigation_rnd.ui.theme.DecomposenavigationrndTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val root = DefaultRootComponent(componentContext = defaultComponentContext())

        setContent {
            DecomposenavigationrndTheme {
                RootContent(component = root)
            }
        }
    }
}