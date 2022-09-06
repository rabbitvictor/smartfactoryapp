package com.mc855.app.http.model

import com.mc855.app.http.client.KtorClient
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserEntity(
	val userId: String,
	val id: String,
	val title: String,
	val completed: Boolean
)

suspend fun getUsers(): List<UserEntity> {
	return KtorClient.httpClient.run {
		get("https://jsonplaceholder.typicode.com/todos").body()
	}
}