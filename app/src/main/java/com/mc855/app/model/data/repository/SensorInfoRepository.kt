package com.mc855.app.model.data.repository

import com.mc855.app.model.data.dao.SensorInfoDao
import com.mc855.app.model.data.tables.SensorInfoRoom
import kotlinx.coroutines.flow.Flow

interface SensorInfoRepository {
	suspend fun getSensorInfoList(sensorId: String): Flow<List<SensorInfoRoom>>
	suspend fun insertSensorInfo(sensorInfo: SensorInfoRoom)
	suspend fun updateSensorInfo(sensorInfo: SensorInfoRoom)
	suspend fun deleteSensorInfo(sensorInfo: SensorInfoRoom)
}


class SensorInfoRepositoryImpl(val sensorInfoDao: SensorInfoDao) : SensorInfoRepository {
	override suspend fun getSensorInfoList(sensorId: String): Flow<List<SensorInfoRoom>> =
		sensorInfoDao.getSensorInfoList(sensorId)

	override suspend fun insertSensorInfo(sensorInfo: SensorInfoRoom): Unit =
		sensorInfoDao.insertSensorInfo(sensorInfo)

	override suspend fun updateSensorInfo(sensorInfo: SensorInfoRoom): Unit =
		sensorInfoDao.updateSensorInfo(sensorInfo)

	override suspend fun deleteSensorInfo(sensorInfo: SensorInfoRoom): Unit =
		sensorInfoDao.deleteSensorInfo(sensorInfo)
}