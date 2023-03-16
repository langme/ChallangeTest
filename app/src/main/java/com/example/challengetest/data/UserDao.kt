package com.example.challengetest.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow


//TODO maybe implement Livedata
@Dao
interface UserDao {
    @Query("SELECT * FROM user ORDER BY id")
    fun getUsers(): Flow<List<RegisterUser>>

    @Query("SELECT * FROM user WHERE id = :idUser")
    fun getUsers(idUser: String): LiveData<RegisterUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(RegisterUser: List<RegisterUser>)

    @Insert
    suspend fun insert(item: RegisterUser)

    @Update
    suspend fun update(item: RegisterUser)

    @Delete
    suspend fun delete(item: RegisterUser)

    @Query("DELETE FROM user")
    suspend fun deleteAllUsers()
}