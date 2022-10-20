package com.mc855.app.model.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import androidx.room.Update
import com.mc855.app.model.data.tables.KeycloakGroupRoom
import com.mc855.app.model.utils.KC_GROUP_TABLE
import kotlinx.coroutines.flow.Flow


@Dao
interface KeycloakGroupDao {
	@Query("SELECT * FROM $KC_GROUP_TABLE")
	fun getAllGroups(): Flow<List<KeycloakGroupRoom>>

	@Query("SELECT * FROM $KC_GROUP_TABLE WHERE kcId = :kcId")
	fun getGroup(kcId: String): Flow<KeycloakGroupRoom>

	@Insert(onConflict = IGNORE)
	fun insertGroup(keycloakUser: KeycloakGroupRoom)

	@Update
	fun updateGroup(keycloakUser: KeycloakGroupRoom)

	@Delete
	fun deleteGroup(keycloakUser: KeycloakGroupRoom)
}

