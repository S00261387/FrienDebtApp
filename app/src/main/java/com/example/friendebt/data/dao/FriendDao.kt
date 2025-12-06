package com.example.friendebt.data.dao

import androidx.room.*
import com.example.friendebt.data.Friend
import kotlinx.coroutines.flow.Flow

@Dao
interface FriendDao {

    @Query("SELECT * FROM friends ORDER BY name ASC")
    fun getAllFriends(): Flow<List<Friend>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFriend(friend: Friend)

    @Delete
    suspend fun deleteFriend(friend: Friend)
}
