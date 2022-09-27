package com.mc855.app.model.network

import com.mc855.app.model.network.client.KtorClient
import com.mc855.app.model.network.model.SensorEntity
import io.ktor.client.call.*
import io.ktor.client.request.*

object SensorRepository {
	suspend fun fetchSensors(): List<SensorEntity> {
		return KtorClient.httpClient.run {
			get("http://172.17.0.1:8000/sensorsInfo").body()
		}
	}
}