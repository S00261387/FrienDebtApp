package com.example.friendebt.viewmodel

import androidx.lifecycle.ViewModel
import com.example.friendebt.data.Friend
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FriendViewModel : ViewModel() {

    private val _friends = MutableStateFlow<List<Friend>>(emptyList())
    val friends: StateFlow<List<Friend>> = _friends

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
}
