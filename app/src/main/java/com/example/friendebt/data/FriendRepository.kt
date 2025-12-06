package com.example.friendebt.data

import androidx.lifecycle.LifecycleOwner
import com.example.friendebt.data.dao.DebtDao
import com.example.friendebt.data.dao.FriendDao
import com.example.friendebt.data.Friend
import com.example.friendebt.data.FriendDebt
import kotlinx.coroutines.flow.Flow

class FriendRepository(
    private val friendDao: FriendDao,
    private val debtDao: DebtDao
) {

    val friends: Flow<List<Friend>> = friendDao.getAllFriends()

    fun getDebtsForFriend(friendId: Int): Flow<List<FriendDebt>> =
        debtDao.getDebtsForFriend(friendId)

    suspend fun addFriend(friend: Friend) {
        friendDao.addFriend(friend)
    }

    suspend fun removeFriend(friend: Friend) {
        friendDao.deleteFriend(friend)
    }

    suspend fun addDebt(debt: FriendDebt) {
        debtDao.addDebt(debt)
    }

    suspend fun removeDebt(debt: FriendDebt) {
        debtDao.deleteDebt(debt)
    }
}
