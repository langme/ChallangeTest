package com.example.challengetest.repository


import com.example.challengetest.data.RegisterUser
import com.example.challengetest.data.UserDao
import kotlinx.coroutines.flow.Flow

class UserRepository (private val userDao: UserDao){
    val allUser: Flow<List<RegisterUser>> = userDao.getUsers()

    suspend fun addUserRepo(userItem: RegisterUser) {
        userDao.insert(userItem)
    }

    fun getAllUsersRepo(): Flow<List<RegisterUser>> {
        return allUser
    }
}