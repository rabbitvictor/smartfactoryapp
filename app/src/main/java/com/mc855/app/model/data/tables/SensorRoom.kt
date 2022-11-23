package com.mc855.app.model.data.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mc855.app.model.utils.SENSOR_TABLE


@Entity(tableName = SENSOR_TABLE)
data class SensorRoom(
	@PrimaryKey(autoGenerate = true) val id: Int = 0,
	val sensorId: String,
	val sensorName: String,
	val locatedAt: String,
)