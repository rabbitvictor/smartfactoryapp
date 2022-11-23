package com.mc855.app.model.data.repository

import com.mc855.app.model.data.dao.SensorDao
import com.mc855.app.model.data.tables.SensorRoom
import kotlinx.coroutines.flow.Flow

interface SensorRepository {
	suspend fun getSensor(sensorId: String): Flow<SensorRoom>
	suspend fun insertSensor(sensor: SensorRoom)
	suspend fun updateSensor(sensor: SensorRoom)
	suspend fun deleteSensor(sensor: SensorRoom)
}


class SensorRepositoryImpl(val sensorDao: SensorDao): SensorRepository {
	override suspend fun getSensor(sensorId: String): Flow<SensorRoom> = sensorDao.getSensor(sensorId)
	override suspend fun insertSensor(sensor: SensorRoom): Unit = sensorDao.insertSensor(sensor)
	override suspend fun updateSensor(sensor: SensorRoom): Unit = sensorDao.updateSensor(sensor)
	override suspend fun deleteSensor(sensor: SensorRoom): Unit = sensorDao.deleteSensor(sensor)
}