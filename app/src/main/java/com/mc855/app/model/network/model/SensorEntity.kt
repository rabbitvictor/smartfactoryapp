package com.mc855.app.model.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SensorEntity(
	val id: Int,
	@SerialName("sensor_name")
	val sensorName: String,
	val metrics: List<MetricsEntity>,
	@SerialName("has_exceeded_limit")
	val hasExceededLimit: Boolean
)