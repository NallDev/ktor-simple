package com.nalldev.routes

import com.nalldev.models.User
import com.nalldev.services.IUserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val userService by inject<IUserService>()

    routing {
        route("/users") {
            get {
                val users = userService.getAllUser()
                call.respond(users)
            }
            get("{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID tidak valid")
                    return@get
                }
                val user = userService.getUser(id)
                if (user == null) {
                    call.respond(HttpStatusCode.NotFound, "User tidak ditemukan")
                } else {
                    call.respond(user)
                }
            }
            post {
                val user = call.receive<User>()
                userService.addUser(user)
                call.respond(HttpStatusCode.Created, "User berhasil ditambahkan")
            }
            put("{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID tidak valid")
                    return@put
                }
                val user = call.receive<User>()
                val updated = userService.updateUser(id, user)
                if (updated) {
                    call.respond(HttpStatusCode.OK, "User berhasil diperbarui")
                } else {
                    call.respond(HttpStatusCode.NotFound, "User tidak ditemukan")
                }
            }
            delete("{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID tidak valid")
                    return@delete
                }
                val deleted = userService.deleteUser(id)
                if (deleted) {
                    call.respond(HttpStatusCode.OK, "User berhasil dihapus")
                } else {
                    call.respond(HttpStatusCode.NotFound, "User tidak ditemukan")
                }
            }
        }
    }
}
