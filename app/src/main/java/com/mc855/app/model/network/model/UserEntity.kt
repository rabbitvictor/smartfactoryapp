package com.mc855.app.model.network.model

import com.mc855.app.model.network.client.KtorClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable

@Serializable
data class UserEntity(
	val userId: String,
	val id: String,
	val title: String,
	val completed: Boolean
)