package com.mc855.app.model.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KeycloakUserEntity(
	@SerialName("id") val kcId: String,
	val username: String,
	@SerialName("first_name") val firstName: String?,
	@SerialName("last_name") val lastName: String?,
	@SerialName("created_timestamp") val createdTimestap: Double,
	val groups: List<KeycloakGroupEntity>,
	val token: String,
)

@Serializable
data class KeycloakGroupEntity(
	@SerialName("id") val kcId: String,
	val name: String,
	val path: String,
)