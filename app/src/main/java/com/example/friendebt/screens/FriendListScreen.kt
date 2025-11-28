package com.example.friendebt.screens

import com.example.friendebt.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.friendebt.data.Friend
import coil.compose.AsyncImage
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme

@Composable
fun FriendListScreen(
    friends: List<Friend>,
    onAddFriendClick: () -> Unit,
    onFriendClick: (Int) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddFriendClick) {
                Text("+")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            Text(
                text = "Friend List",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn {
                items(friends) { friend ->
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { onFriendClick(friend.id) },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = friend.imageUri ?: R.drawable.ic_placeholder, //default image if no real picture taken
                            contentDescription = "Friend Image",
                            modifier = Modifier
                                .size(50.dp)
                                .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), shape = CircleShape)
                                .padding(3.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = friend.name,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}