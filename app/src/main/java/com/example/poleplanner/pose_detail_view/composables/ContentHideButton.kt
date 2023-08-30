package com.example.poleplanner.pose_detail_view.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.poleplanner.ui.theme.DarkPink

@Preview
@Composable
fun ContentHideButton (
    text: String = "Opis"
) {
    Button(
        onClick = { /*todo: otwarcie opisu*/ },
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(containerColor = DarkPink),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "open description")
            Text(
                text = text,
                textAlign = TextAlign.Center)
        }
    }
}