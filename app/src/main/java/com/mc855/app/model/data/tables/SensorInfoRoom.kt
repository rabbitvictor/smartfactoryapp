package com.mc855.app.model.data.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mc855.app.model.utils.SENSOR_INFO_TABLE

@Entity(tableName = SENSOR_INFO_TABLE)
data class SensorInfoRoom(
	@PrimaryKey(autoGenerate = true) val id: Int = 0,
	val sensorId: Int,
	val type: String,
	val unity: String,
	val upperLimit: Long,
	val bottomLimit: Long,
	val lastValue: Long,
)
