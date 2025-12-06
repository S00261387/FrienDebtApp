package com.example.friendebt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.friendebt.screens.*
import com.example.friendebt.viewmodel.FriendViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: FriendViewModel =
            ViewModelProvider(this)[FriendViewModel::class.java]

        setContent {
            FriendApp(viewModel)
        }
    }
}

@Composable
fun FriendApp(viewModel: FriendViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "friendList"
    ) {

        composable("friendList") {
            FriendListScreen(
                friends = viewModel.friends.collectAsState().value,
                onAddFriendClick = { navController.navigate("addFriend") },
                onFriendClick = { id -> navController.navigate("friendDetails/$id") }
            )
        }

        composable("friendDetails/{friendId}") { entry ->
            val friendId = entry.arguments?.getString("friendId")?.toInt() ?: return@composable

            FriendDetailsScreen(
                friendId = friendId,
                viewModel = viewModel,
                onAddDebtClick = { navController.navigate("addDebt/$friendId") },
                onDeleteClick = {
                    viewModel.removeFriend(friendId)
                    navController.popBackStack()
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("addFriend") {
            val pendingImage = viewModel.pendingImageUri.collectAsState().value

            AddFriendScreen(
                imageUri = pendingImage,
                onTakePictureClick = {
                    navController.navigate("camera")
                },
                onSaveClick = { name, uri ->
                    viewModel.addFriend(name, uri)
                    viewModel.clearPendingImage()
                    navController.popBackStack()
                },
                onBackClick = {
                    viewModel.clearPendingImage()
                    navController.popBackStack()
                }
            )
        }

        composable("camera") {
            CameraScreen(
                onImageCaptured = { uri ->
                    viewModel.setPendingImage(uri.toString())
                },
                onClose = {
                    navController.popBackStack()
                }
            )
        }

        composable("addDebt/{friendId}") { entry ->
            val friendId = entry.arguments?.getString("friendId")?.toInt() ?: return@composable

            AddDebtScreen(
                friendId = friendId,
                onSaveClick = { desc, amount ->
                    viewModel.addDebt(friendId, desc, amount)
                    navController.popBackStack()
                },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
