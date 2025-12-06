package com.example.friendebt.data.dao

import androidx.room.*
import com.example.friendebt.data.FriendDebt
import kotlinx.coroutines.flow.Flow

@Dao
interface DebtDao {

    @Query("SELECT * FROM debts WHERE friendId = :friendId")
    fun getDebtsForFriend(friendId: Int): Flow<List<FriendDebt>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDebt(debt: FriendDebt)

    @Delete
    suspend fun deleteDebt(debt: FriendDebt)
}
