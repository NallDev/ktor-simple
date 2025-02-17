package com.nalldev.database

import org.jetbrains.exposed.sql.Database

class DatabaseManager {
    val database: Database = try {
        Database.connect(
            url = "jdbc:mysql://localhost:3306/test",
            driver = "com.mysql.cj.jdbc.Driver",
            user = "root",
            password = ""
        )
    } catch (e: Exception) {
        println("Database connection error: ${e.message}")
        throw e
    }
}
