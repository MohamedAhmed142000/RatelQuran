package com.example.ratelquran

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ratelquran.presentation.nav.AppNavGraph
import com.example.ratelquran.ui.theme.RatelQuranTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RatelQuranTheme {
                AppNavGraph()

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RatelQuranTheme {
        AppNavGraph()
    }
}