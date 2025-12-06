package com.example.friendebt.viewmodel

import androidx.lifecycle.ViewModel
import com.example.friendebt.data.Friend
import com.example.friendebt.data.FriendDebt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FriendViewModel : ViewModel() {

    private val _friends = MutableStateFlow<List<Friend>>(emptyList())
    val friends: StateFlow<List<Friend>> = _friends
    private val _debts = MutableStateFlow<List<FriendDebt>>(emptyList())
    val debts: StateFlow<List<FriendDebt>> = _debts


    // Temporary image captured for the friend being created
    private val _pendingImageUri = MutableStateFlow<String?>(null)
    val pendingImageUri: StateFlow<String?> = _pendingImageUri

    fun setPendingImage(uri: String?) {
        _pendingImageUri.value = uri
    }

    fun clearPendingImage() {
        _pendingImageUri.value = null
    }

    fun addFriend(name: String, imageUri: String?) {
        val updatedList = _friends.value.toMutableList()
        val newId = if (updatedList.isEmpty()) 1 else updatedList.maxOf { it.id } + 1
        updatedList.add(Friend(id = newId, name = name, imageUri = imageUri))
        _friends.value = updatedList
    }

    fun removeFriend(id: Int) {
        _friends.value = _friends.value.filter { it.id != id }
    }

    fun addDebt(friendId: Int, description: String, amount: Double) {
        val updatedList = _debts.value.toMutableList()
        val newId = if (updatedList.isEmpty()) 1 else updatedList.maxOf { it.id } + 1

        updatedList.add(
            FriendDebt(
                id = newId,
                friendId = friendId,
                description = description,
                amount = amount
            )
        )
        _debts.value = updatedList
    }

    fun removeDebt(id: Int) {
        _debts.value = _debts.value.filter { it.id != id }
    }

    fun getDebtsForFriend(friendId: Int): List<FriendDebt> {
        return _debts.value.filter { it.friendId == friendId }
    }
}
