package com.example.friendebt.viewmodel

import android.app.Application
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.friendebt.data.Friend
import com.example.friendebt.data.FriendDebt

class FriendViewModel(application: Application) : AndroidViewModel(application) {

    private val _friends = MutableStateFlow<List<Friend>>(emptyList())
    val friends: StateFlow<List<Friend>> = _friends
    private val _debts = MutableStateFlow<List<FriendDebt>>(emptyList())
    val debts: StateFlow<List<FriendDebt>> = _debts

    fun addFriend(name: String, imageUri: String?) {
        val updatedList = _friends.value.toMutableList()
        val newId = if (updatedList.isEmpty()) 1 else updatedList.maxOf { it.id } + 1
        updatedList.add(Friend(id = newId, name = name, imageUri = imageUri))
        _friends.value = updatedList
    }

    fun removeFriend(id: Int) {
        _friends.value = _friends.value.filter { it.id != id }
    }

    fun hasCamera(): Boolean {
        val pm = getApplication<Application>().packageManager
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
    }

    fun addDebt(friendId: Int, description: String, amount: Double) {
        val updatedList = _debts.value.toMutableList()
        val newId = if (updatedList.isEmpty()) 1 else updatedList.maxOf { it.id } + 1 //unique id for each friend

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

    fun getTotalForFriend(friendId: Int): Double {
        return getDebtsForFriend(friendId).sumOf { it.amount }
    }
}
