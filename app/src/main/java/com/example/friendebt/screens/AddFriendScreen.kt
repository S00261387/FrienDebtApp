package com.example.friendebt.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun AddFriendScreen(
    onTakePictureClick: () -> Unit,
    onSaveClick: (String, String?) -> Unit,
    onBackClick: () -> Unit
) {
    var friendName by rememberSaveable { mutableStateOf("") }
    var imageUri by rememberSaveable { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onBackClick) {
                Text("X")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextField(
                value = friendName,
                onValueChange = { friendName = it },
                label = { Text("Friend Name") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onTakePictureClick() }
            ) {
                Text("Take Picture")
            }

            imageUri?.let {
                Text("Picture Added!", Modifier.padding(top = 8.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = {
                if (friendName.isBlank()) {
                    Toast.makeText(context, "Enter a name", Toast.LENGTH_SHORT).show()
                } else {
                    onSaveClick(friendName, imageUri)
                }
            }) {
                Text("Save Friend")
            }
        }
    }
}
