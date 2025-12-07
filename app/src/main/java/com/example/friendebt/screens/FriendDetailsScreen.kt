package com.example.friendebt.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.friendebt.viewmodel.FriendViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendDetailsScreen(
    friendId: Int,
    viewModel: FriendViewModel,
    onAddDebtClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val friend = viewModel.friends.collectAsState().value.firstOrNull { it.id == friendId }
    val debts = viewModel.getDebtsForFriend(friendId).collectAsState().value

    if (friend == null) {
        Text("Friend not found", modifier = Modifier.padding(16.dp))
        return
    }

    val total = debts.sumOf { it.amount }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(friend.name) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Text("<")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Total owed: €${"%.2f".format(total)}",
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(debts) { debt ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(debt.description, fontSize = 18.sp)
                            Text(
                                "€${debt.amount}",
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

            Button(
                onClick = onAddDebtClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("+ Add Debt")
            }

            Button(
                onClick = onDeleteClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Delete Friend")
            }
        }
    }
}
