package com.example.poleplanner.pose_detail_view.composables

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.poleplanner.R
import com.example.poleplanner.data_structure.models.Difficulty
import com.example.poleplanner.ui.theme.veryLightGrey


@Composable
fun PhotoBox(
    difficulty: Difficulty,
    photoResId: Int = R.drawable.pd,
    photoUri: Uri = Uri.EMPTY
)  {
    Box {
        if (photoUri == Uri.EMPTY) {
            Photo(photoResId = photoResId)
        } else {
            Photo(photoUri = photoUri)
        }
        Text(
            text = difficulty.name,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(5.dp)
                .alpha(0.8f)
                .background(color = veryLightGrey)
                .padding(5.dp)
                .align(Alignment.BottomEnd)
        )
    }
}


@Composable
fun Photo (
    photoResId: Int = R.drawable.pd,
    photoUri: Uri = Uri.EMPTY
) {
    if (photoUri == Uri.EMPTY) {
        Image(
            painter = painterResource(id = photoResId),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
    } else {
        val context = LocalContext.current
        try {
            if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images
                    .Media.getBitmap(context.contentResolver, photoUri)
                } else {
                    val source = ImageDecoder
                        .createSource(context.contentResolver, photoUri)
                    ImageDecoder.decodeBitmap(source)
                }
        } catch (e: Exception) {
            e.message?.let { Log.d("Photo", it) }
        }

        val bitmap: Bitmap? =
            if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images
                    .Media.getBitmap(context.contentResolver, photoUri)
                } else {
                    val source = ImageDecoder
                        .createSource(context.contentResolver, photoUri)
                    ImageDecoder.decodeBitmap(source)
                }

        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(id = photoResId),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }
    }
}