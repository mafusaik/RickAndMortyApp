package com.mafusaik.rickandmortyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mafusaik.rickandmortyapp.navigation.NavigationHost
import com.mafusaik.rickandmortyapp.ui.theme.RickyAndMortiAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickyAndMortiAppTheme {
                NavigationHost()
            }
        }
    }
}

