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
fun AddDebtScreen(
    friendId: Int,
    onSaveClick: (String, Double) -> Unit,
    onBackClick: () -> Unit
) {
    var description by rememberSaveable { mutableStateOf("") }
    var amountText by rememberSaveable { mutableStateOf("") }

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
                value = description,
                onValueChange = { description = it },
                label = { Text("Description of debt") },
                modifier = Modifier.padding(16.dp)
            )

            TextField(
                value = amountText,
                onValueChange = { amountText = it },
                label = { Text("Amount (€)") },
                modifier = Modifier.padding(16.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val amount = amountText.toDoubleOrNull()

                    when {
                        description.isBlank() ->
                            Toast.makeText(context, "Enter a description", Toast.LENGTH_SHORT).show()

                        amount == null ->
                            Toast.makeText(context, "Enter a valid number", Toast.LENGTH_SHORT).show()

                        else -> {
                            onSaveClick(description, amount)
                        }
                    }
                }
            ) {
                Text("Save Debt")
            }
        }
    }
}
