package com.mc855.app.model.network

import com.mc855.app.model.network.client.KtorClient
import com.mc855.app.model.network.model.KeycloakUserEntity
import com.mc855.app.model.network.model.SensorInfoEntity
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

object SensorInfoRepo {
	suspend fun getSensorInfoList(token: String): List<SensorInfoEntity> {
		val response = KtorClient.httpClient.get {
			url("http://172.19.0.1:8000/sensors/list")
			header("Authorization", token)
		}

		return if (response.status.value == 200) {
			response.body()
		} else {
			throw RuntimeException("INVALID USER CREDENTIALS")
		}

	}
}