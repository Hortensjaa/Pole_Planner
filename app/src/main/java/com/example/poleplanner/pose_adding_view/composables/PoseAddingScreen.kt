package com.example.poleplanner.pose_adding_view.composables

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.poleplanner.data_structure.models.Difficulty
import com.example.poleplanner.navbar.Screen
import com.example.poleplanner.pose_adding_view.PoseAddingEvent
import java.util.Locale


// todo: rozwiązać sprawę tagów
@Composable
fun PoseAddingScreen(
    addingOnEvent: (PoseAddingEvent) -> Unit,
    navController: NavController
) {
    var name by remember { mutableStateOf("") }
    var difficulty by remember { mutableStateOf(Difficulty.BEGINNER) }
    var description by remember { mutableStateOf("") }
    var photo by remember { mutableStateOf(Uri.EMPTY) }

    val scrollState = rememberScrollState(0)
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(vertical = 15.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Dodaj nową figurę",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        )
        NameField(onValueChange = { newName -> name =
            newName.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault())
                else it.toString() }
        })
        Box (
            modifier = Modifier.padding(horizontal = 30.dp)
        ) {
            ImageBox(onValueChange = { newPhoto -> photo = newPhoto })
        }
        DifficultyDropdownMenu(onValueChange = { newDiff -> difficulty = newDiff })
        TagsCheckboxes()
        DescriptionField(onValueChange = { newDesc -> description = newDesc })
        Button(
            enabled = (name != ""),
            onClick = {
                addingOnEvent(PoseAddingEvent.SavePose(name, description, difficulty, photo))
                navController.navigate("${Screen.DetailScreen.route}/${name}")
        }) {
            Text("Zapisz")
        }
    }
}
