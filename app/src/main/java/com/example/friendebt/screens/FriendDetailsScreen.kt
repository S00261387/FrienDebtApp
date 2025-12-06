package com.example.friendebt.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.friendebt.viewmodel.FriendViewModel

@Composable
fun FriendDetailsScreen(
    friendId: Int,
    viewModel: FriendViewModel,
    onAddDebtClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val friend = viewModel.friends.collectAsState().value.firstOrNull { it.id == friendId }
    val debts = viewModel.getDebtsForFriend(friendId)

    if (friend == null) {
        Text("Friend not found", modifier = Modifier.padding(16.dp))
        return
    }

    val total = debts.sumOf { it.amount }

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
                .padding(padding)
        ) {

            Text(
                text = "💳 Debts for ${friend.name}",
                fontSize = 22.sp,
                modifier = Modifier.padding(16.dp)
            )

            Text(
                text = "TOTAL: €${"%.2f".format(total)}",
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(Modifier.height(14.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(debts) { debt ->
                    Text(
                        "${debt.description}: €${debt.amount}",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            Button(
                onClick = { onAddDebtClick() },
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
