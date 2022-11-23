package com.mc855.app.model.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SensorInfoEntity(
	@SerialName("sensor_id") val sensorId: String,
	val name: String,
	@SerialName("located_at") val locatedAt: String?,
	val info: List<SensorInfo>?,
	val groups: List<String>,
)

fun SensorInfoEntity.hasExceededLimit(): Boolean {
	if(info?.isEmpty() == true) return false
	val info = info?.first() ?: return false
	if (info.lastValue == null) return false

	val exceedsUpper = if (info.upperLimit != null) {
		info.lastValue.toDouble() > info.upperLimit.toDouble()
	} else {
		null
	}

	val exceedsLower = if (info.bottomLimit != null) {
		info.lastValue.toDouble() < info.bottomLimit.toDouble()
	} else {
		null
	}

	return exceedsUpper == true || exceedsLower == true
}

@Serializable
data class SensorInfo(
	val type: String,
	val unity: String,
	@SerialName("upper_limit") val upperLimit: String?,
	@SerialName("bottom_limit") val bottomLimit: String?,
	@SerialName("last_value") val lastValue: String?,
)
