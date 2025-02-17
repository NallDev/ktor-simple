package com.nalldev.services

import com.nalldev.models.User

interface IUserService {
    suspend fun addUser(user: User)
    suspend fun getAllUser(): List<User>
    suspend fun getUser(id: Int): User?
    suspend fun updateUser(id: Int, user: User): Boolean
    suspend fun deleteUser(id: Int): Boolean
}