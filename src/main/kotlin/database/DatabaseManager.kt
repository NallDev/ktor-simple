package com.nalldev.database

import org.ktorm.database.Database

class DatabaseManager {
    private lateinit var database: Database

    init {
        try {
            database = Database.connect(
                url = "jdbc:mysql://localhost:3306/test",
                driver = "com.mysql.cj.jdbc.Driver",
                user = "root",
                password = ""
            )
        } catch (e: Exception) {
            println("ERROR ${e.message}")
        }

    }
}