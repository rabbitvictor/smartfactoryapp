package com.mc855.app.model.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.mc855.app.model.data.tables.SensorRoom
import com.mc855.app.model.utils.SENSOR_TABLE
import kotlinx.coroutines.flow.Flow


@Dao
interface SensorDao {
	@Query("SELECT * FROM $SENSOR_TABLE WHERE sensorId = :sensorId")
	fun getSensor(sensorId: String): Flow<SensorRoom>
	@Insert(onConflict = REPLACE)
	fun insertSensor(sensor: SensorRoom)
	@Update
	fun updateSensor(sensor: SensorRoom)
	@Delete
	fun deleteSensor(sensor: SensorRoom)
}