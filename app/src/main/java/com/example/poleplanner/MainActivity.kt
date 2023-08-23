package com.example.poleplanner


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import com.example.poleplanner.navbar.NavBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavBar { modifier ->
                Column(
                    modifier = modifier
                ) {
                    Text("Witaj w naszej aplikacji")
                }
            }
        }
    }
}









