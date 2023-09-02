package com.example.poleplanner.pose_of_a_day.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.poleplanner.data_structure.Pose
import com.example.poleplanner.ui.theme.AlmostWhite
import com.example.poleplanner.ui.theme.ProgressRow
import com.example.poleplanner.ui.theme.TagBox
import com.example.poleplanner.ui.theme.Typography

@Preview
@Composable
fun Front(
    modifier: Modifier = Modifier,
    pose: Pose = Pose("Placeholder pose")
) {
    Column(modifier = modifier.padding(20.dp)) {
        Text(
            text = pose.poseName,
            color = Color.White,
            style = Typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        )
        Image(
            painter = painterResource(id = pose.photoResId),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .horizontalScroll(rememberScrollState())
        ) {
            listOf("statyczne", "duety", "szpagaty").forEach { tagName ->
                TagBox(tagName = tagName,
                    action = {})
            }
        }
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = pose.difficulty.toString(),
                color = AlmostWhite,
                style = Typography.titleMedium
            )
            ProgressRow(
                progress = pose.progress,
                color = MaterialTheme.colorScheme.secondary
            )
        }

    }
}