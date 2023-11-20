package com.example.poleplanner.pose_adding_view.composables

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.poleplanner.navbar.Screen


@Composable
fun PermissionsScreen(
    navController: NavController
) {

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Accepted: Do something
            Log.d("ExampleScreen","PERMISSION GRANTED")
            navController.navigate(Screen.PoseAddingScreen.route)
        } else {
            // Permission Denied: Do something
            Log.d("ExampleScreen","PERMISSION DENIED")
        }
    }
    val context = LocalContext.current

    Button(
        onClick = {
            // Check permission
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) -> {
                    // Some works that require permission
                    Log.d("ExampleScreen","Code requires permission")
                }
                else -> {
                    // Asking for permission
                    launcher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }
            }
        }
    ) {
        Text(text = "Check and Request Permission")
    }
}
