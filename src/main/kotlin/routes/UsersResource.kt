package com.nalldev.routes

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

@Serializable
@Resource("/users")
class UsersResource {
    @Serializable
    @Resource("{id}")
    data class WithId(val parent: UsersResource = UsersResource(), val id: Int)
}
