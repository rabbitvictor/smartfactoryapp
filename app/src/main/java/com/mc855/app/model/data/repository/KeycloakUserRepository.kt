package com.mc855.app.model.data.repository

import com.mc855.app.model.data.dao.KeycloakUserDao
import com.mc855.app.model.data.tables.KeycloakUserRoom
import kotlinx.coroutines.flow.Flow

interface KeycloakUserRepository {
	fun getUser(kcId: String): KeycloakUserRoom
	fun getAllUsers(): List<KeycloakUserRoom>
	suspend fun insertUser(keycloakUser: KeycloakUserRoom)
	suspend fun updateUser(keycloakUser: KeycloakUserRoom)
	suspend fun deleteUser(keycloakUser: KeycloakUserRoom)
}


class KeycloakUserRepositoryImpl(val kcUserDao: KeycloakUserDao) : KeycloakUserRepository {
	override fun getUser(kcId: String) = kcUserDao.getUser(kcId)
	override fun getAllUsers() = kcUserDao.getAllUsers()

	override suspend fun insertUser(keycloakUser: KeycloakUserRoom) =
		kcUserDao.insertUser(keycloakUser)

	override suspend fun updateUser(keycloakUser: KeycloakUserRoom) =
		kcUserDao.updateUser(keycloakUser)

	override suspend fun deleteUser(keycloakUser: KeycloakUserRoom) =
		kcUserDao.deleteUser(keycloakUser)
}