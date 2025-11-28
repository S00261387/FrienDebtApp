package com.example.friendebt.data

data class FriendDebt(
    val id: Int,
    val friendId: Int,
    val description: String,
    val amount: Double,
)