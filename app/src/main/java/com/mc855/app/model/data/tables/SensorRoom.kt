package com.mc855.app.model.data.tables


data class SensorRoom(
	val id: Int,
	val sensorName: String,
	val metrics: List<MetricsRoom>,
	val hasExceededLimit: Boolean
)