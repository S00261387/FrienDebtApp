package com.example.friendebt.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.friendebt.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FriendViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val repository = FriendRepository(db.friendDao(), db.debtDao())

    // StateFlows observed by UI
    val friends = repository.friends
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val _pendingImageUri = MutableStateFlow<String?>(null)
    val pendingImageUri = _pendingImageUri

    fun setPendingImage(uri: String?) {
        _pendingImageUri.value = uri
    }

    fun clearPendingImage() {
        _pendingImageUri.value = null
    }

    fun getDebtsForFriend(friendId: Int) =
        repository.getDebtsForFriend(friendId)
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun addFriend(name: String, imageUri: String?) {
        viewModelScope.launch {
            repository.addFriend(Friend(name = name, imageUri = imageUri))
        }
    }

    fun removeFriend(id: Int) {
        val friend = friends.value.firstOrNull { it.id == id } ?: return
        viewModelScope.launch { repository.removeFriend(friend) }
    }

    fun addDebt(friendId: Int, desc: String, amount: Double) {
        viewModelScope.launch {
            repository.addDebt(FriendDebt(friendId = friendId, description = desc, amount = amount))
        }
    }

    fun removeDebt(debt: FriendDebt) {
        viewModelScope.launch { repository.removeDebt(debt) }
    }
}
