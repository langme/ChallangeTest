package com.example.challengetest.repository


import androidx.lifecycle.LiveData
import com.example.challengetest.data.RegisterUser
import com.example.challengetest.data.UserDao

class UserRepository (private val userDao: UserDao){
    val allUser: LiveData<List<RegisterUser>> = userDao.getUsers()

    suspend fun addUserRepo(userItem: RegisterUser) {
        userDao.insert(userItem)
    }
}