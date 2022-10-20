package com.mc855.app.model.data.repository

import com.mc855.app.model.data.dao.KeycloakGroupDao
import com.mc855.app.model.data.tables.KeycloakGroupRoom
import kotlinx.coroutines.flow.Flow

interface KeycloakGroupRepository {
	suspend fun getAllGroups(): Flow<List<KeycloakGroupRoom>>
	suspend fun getGroup(kcId: String): Flow<KeycloakGroupRoom>
	suspend fun insertGroup(keycloakGroup: KeycloakGroupRoom)
	suspend fun updateGroup(keycloakGroup: KeycloakGroupRoom)
	suspend fun deleteGroup(keycloakGroup: KeycloakGroupRoom)
}


class KeycloakGroupRepositoryImpl(val kcGroupDao: KeycloakGroupDao) : KeycloakGroupRepository {
	override suspend fun getAllGroups() = kcGroupDao.getAllGroups()

	override suspend fun getGroup(kcId: String) = kcGroupDao.getGroup(kcId)

	override suspend fun insertGroup(keycloakGroup: KeycloakGroupRoom) =
		kcGroupDao.insertGroup(keycloakGroup)

	override suspend fun updateGroup(keycloakGroup: KeycloakGroupRoom) =
		kcGroupDao.updateGroup(keycloakGroup)

	override suspend fun deleteGroup(keycloakGroup: KeycloakGroupRoom) =
		kcGroupDao.deleteGroup(keycloakGroup)
}