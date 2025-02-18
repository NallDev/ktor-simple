package com.nalldev.routes

import com.nalldev.models.User
import com.nalldev.services.IUserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import io.ktor.server.resources.post as resourcesPost
import io.ktor.server.resources.put as resourcesPut

fun Application.configureRouting() {
    val userService by inject<IUserService>()

    install(Resources)

    routing {
        get<UsersResource> {
            val users = userService.getAllUser()
            call.respond(users)
        }

        get<UsersResource.WithId> { resource ->
            val user = userService.getUser(resource.id)
            if (user == null) {
                call.respond(HttpStatusCode.NotFound, "User tidak ditemukan")
            } else {
                call.respond(user)
            }
        }

        resourcesPost<UsersResource> {
            val user = call.receive<User>()
            userService.addUser(user)
            call.respond(HttpStatusCode.Created, "User berhasil ditambahkan")
        }

        resourcesPut<UsersResource.WithId> { resource ->
            val user = call.receive<User>()
            val updated = userService.updateUser(resource.id, user)
            if (updated) {
                call.respond(HttpStatusCode.OK, "User berhasil diperbarui")
            } else {
                call.respond(HttpStatusCode.NotFound, "User tidak ditemukan")
            }
        }

        delete<UsersResource.WithId> { resource ->
            val deleted = userService.deleteUser(resource.id)
            if (deleted) {
                call.respond(HttpStatusCode.OK, "User berhasil dihapus")
            } else {
                call.respond(HttpStatusCode.NotFound, "User tidak ditemukan")
            }
        }
    }
}
