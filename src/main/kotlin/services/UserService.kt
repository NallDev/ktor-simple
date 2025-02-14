package com.nalldev.services

class UserService : IUserService {

    private val dataTemp = mutableListOf<User>()

    override suspend fun addUser(user: User) {
        val copy = user.copy(id = dataTemp.size + 1)
        dataTemp.add(copy)
    }

    override suspend fun getAllUser(): List<User> {
        return dataTemp
    }

    override suspend fun getUser(id: Int): User? {
        return dataTemp.firstOrNull { it.id == id }
    }

    override suspend fun updateUser(id: Int, user: User) : Boolean {
        val data = dataTemp.find { it.id == id } ?: return false
        dataTemp.replaceAll {
            if (it.id == id) {
                data.copy(name = user.name, age = user.age)
            } else {
                it
            }
        }
        return true
    }

    override suspend fun deleteUser(id: Int) : Boolean {
        return dataTemp.removeIf { it.id == id }
    }

}