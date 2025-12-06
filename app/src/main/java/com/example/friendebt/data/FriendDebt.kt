package com.example.friendebt.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "debts")
data class FriendDebt(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val friendId: Int,
    val description: String,
    val amount: Double
)
