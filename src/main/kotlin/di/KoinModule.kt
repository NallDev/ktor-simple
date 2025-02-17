package com.nalldev.di

import com.nalldev.database.DatabaseManager
import com.nalldev.repository.UserRepository
import com.nalldev.services.IUserService
import org.koin.dsl.module

val appModule = module {
    single { DatabaseManager() }
    single { get<DatabaseManager>().database }
    single<IUserService> { UserRepository(get()) }
}
