package com.mc855.app.model.data.tables

import kotlinx.serialization.SerialName


data class MetricsRoom(
	val id: String,
	@SerialName("metric_name")
	val metricName: String,
	val metric: Int
)
