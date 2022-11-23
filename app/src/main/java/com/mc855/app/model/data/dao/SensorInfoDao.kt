package com.mc855.app.model.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mc855.app.model.data.tables.SensorInfoRoom
import com.mc855.app.model.utils.SENSOR_INFO_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface SensorInfoDao {
	@Query("SELECT * FROM $SENSOR_INFO_TABLE WHERE sensorId = :sensorId")
	fun getSensorInfoList(sensorId: String): Flow<List<SensorInfoRoom>>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertSensorInfo(sensorInfo: SensorInfoRoom)

	@Update
	fun updateSensorInfo(sensorInfo: SensorInfoRoom)

	@Delete
	fun deleteSensorInfo(sensorInfo: SensorInfoRoom)
}