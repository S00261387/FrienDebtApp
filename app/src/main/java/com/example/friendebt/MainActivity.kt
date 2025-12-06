package com.example.friendebt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.friendebt.screens.*
import com.example.friendebt.viewmodel.FriendViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FriendApp()
        }
    }
}

@Composable
fun FriendApp() {
    val navController = rememberNavController()
    val friendViewModel: FriendViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "friendList"
    ) {

        composable("friendList") {
            FriendListScreen(
                friends = friendViewModel.friends.collectAsState().value,
                onAddFriendClick = { navController.navigate("addFriend") },
                onFriendClick = { id -> navController.navigate("friendDetails/$id") }
            )
        }

        composable("friendDetails/{friendId}") { entry ->
            val friendId = entry.arguments?.getString("friendId")?.toInt() ?: return@composable

            FriendDetailsScreen(
                friendId = friendId,
                viewModel = friendViewModel,
                onDeleteClick = {
                    friendViewModel.removeFriend(friendId)
                    navController.popBackStack()
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("addFriend") {
            // Get current pending image from ViewModel
            val pendingImage = friendViewModel.pendingImageUri.collectAsState().value

            AddFriendScreen(
                imageUri = pendingImage,
                onTakePictureClick = {
                    navController.navigate("camera")
                },
                onSaveClick = { name, uri ->
                    friendViewModel.addFriend(name, uri)
                    friendViewModel.clearPendingImage()
                    navController.popBackStack()
                },
                onBackClick = {
                    friendViewModel.clearPendingImage()
                    navController.popBackStack()
                }
            )
        }

        composable("camera") {
            CameraScreen(
                onImageCaptured = { uri ->
                    // Save image in ViewModel
                    friendViewModel.setPendingImage(uri.toString())
                },
                onClose = {
                    navController.popBackStack()
                }
            )
        }
    }
}
