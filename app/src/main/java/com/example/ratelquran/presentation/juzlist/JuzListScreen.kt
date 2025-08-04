package com.example.ratelquran.presentation.juzlist

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ratelquran.data.local.loadJuzList
import com.example.ratelquran.domain.model.JuzModel

@Composable
fun JuzListScreen(
    context: Context, navController: NavController
) {
    var juzList by remember { mutableStateOf(emptyList<JuzModel>()) }

    LaunchedEffect(Unit) {
        juzList = loadJuzList(context)
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(juzList) { juz ->


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        navController.navigate("juz_full/${juz.number}")
                    }
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text("الجزء ${juz.number}", fontSize = 20.sp)

                }
            }
        }
    }
}
