package com.nalldev

import com.nalldev.services.IUserService
import com.nalldev.services.UserService
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureFrameworks() {
    install(Koin) {
        slf4jLogger()
        modules(module {
            single<IUserService>{UserService()}
        })
    }
}