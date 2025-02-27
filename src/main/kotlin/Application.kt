package com.nalldev

import com.nalldev.di.appModule
import com.nalldev.plugins.configureHTTP
import com.nalldev.plugins.configureMonitoring
import com.nalldev.plugins.configureSerialization
import com.nalldev.plugins.security
import com.nalldev.routes.configureRouting
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
    configureSerialization()
    configureMonitoring()
    configureHTTP()
    security()
    configureRouting()
}
