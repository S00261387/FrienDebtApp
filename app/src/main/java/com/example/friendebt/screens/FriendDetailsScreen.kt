package com.example.friendebt.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.friendebt.R
import com.example.friendebt.viewmodel.FriendViewModel

@Composable
fun FriendDetailsScreen(
    friendId: Int,
    viewModel: FriendViewModel,
    onDeleteClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val friend = viewModel.friends.value.firstOrNull { it.id == friendId }

    if (friend == null) {
        onBackClick()
        return
    }

    Scaffold(
        floatingActionButton = { FloatingActionButton(onClick = onBackClick) { Text("X") } }
    ) { padding ->
        Column(
            Modifier.fillMaxSize().padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(16.dp))

            AsyncImage(
                model = friend.imageUri ?: R.drawable.ic_placeholder,
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )

            Spacer(Modifier.height(16.dp))

            Text(friend.name, fontSize = 30.sp)

            Spacer(Modifier.height(24.dp))

            Button(onClick = onDeleteClick) { Text("Delete Friend") }
        }
    }
}
