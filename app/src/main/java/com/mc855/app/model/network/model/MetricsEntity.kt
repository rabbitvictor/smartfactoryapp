package com.mc855.app.model.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MetricsEntity(
	val id: String,
	@SerialName("metric_name")
	val metricName: String,
	val metric: Int
)
