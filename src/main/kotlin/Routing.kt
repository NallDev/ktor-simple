package com.nalldev

import com.nalldev.database.DatabaseManager
import com.nalldev.services.IUserService
import com.nalldev.services.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val userService by inject<IUserService>()
    val database = DatabaseManager()
    routing {
        get("/users") {
            call.respond(userService.getAllUser())
        }

        get("/user/{id}") {
            val params = call.parameters["id"]?.toIntOrNull()

            if (params == null) {
                call.respond(HttpStatusCode.BadRequest, "NOMER COY")
                return@get
            }

            val user = userService.getUser(params)

            if (user == null) {
                call.respond(HttpStatusCode.NotFound, "USER NOT FOUND")
                return@get
            }

            call.respond(HttpStatusCode.OK, user)
        }

        post("/user") {
            val params = call.receive<User>()
            userService.addUser(params)
            call.respond(HttpStatusCode.OK, "JOS BOS")
        }

        put("/user/{id}") {
            val params = call.parameters["id"]?.toIntOrNull()
            val user = call.receive<User>()

            if (params == null) {
                call.respond(HttpStatusCode.BadRequest, "NOMER COY")
                return@put
            }

            val success = userService.updateUser(params, user)
            if (success) {
                call.respond(HttpStatusCode.OK, "GOOD BOY")
            } else {
                call.respond(HttpStatusCode.NotFound, "GA ADA CUY")
            }
        }

        delete("/user/{id}") {
            val params = call.parameters["id"]?.toIntOrNull()

            if (params == null) {
                call.respond(HttpStatusCode.BadRequest, "NOMER COY")
                return@delete
            }

            val success = userService.deleteUser(params)
            if (success) {
                call.respond(HttpStatusCode.OK, "DI HAPUS BOY")
            } else {
                call.respond(HttpStatusCode.NotFound, "GA ADA CUY")
            }
        }
    }
}
