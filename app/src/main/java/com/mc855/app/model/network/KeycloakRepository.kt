package com.mc855.app.model.network

import com.mc855.app.model.network.client.KtorClient
import com.mc855.app.model.network.model.KeycloakUserEntity
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

object KeycloakRepository {
	suspend fun authUser(username: String, password: String): KeycloakUserEntity {
		val response = KtorClient.httpClient.get {
			url("http://172.19.0.1:8000/authUserInfo")
			parameter("username", username)
			parameter("password", password)
			contentType(ContentType.Text.Plain)
		}

		return if (response.status.value == 200) {
			response.body()
		} else {
			throw RuntimeException("INVALID USER CREDENTIALS")
		}

	}
}