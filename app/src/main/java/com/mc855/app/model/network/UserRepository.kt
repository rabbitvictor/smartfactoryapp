package com.mc855.app.model.network

import com.mc855.app.model.network.client.KtorClient
import com.mc855.app.model.network.model.UserEntity
import io.ktor.client.call.*
import io.ktor.client.request.*

object UserRepository {
	suspend fun fetchUsers(): List<UserEntity> {
		return KtorClient.httpClient.run {
			get("https://jsonplaceholder.typicode.com/todos").body()
		}
	}
}