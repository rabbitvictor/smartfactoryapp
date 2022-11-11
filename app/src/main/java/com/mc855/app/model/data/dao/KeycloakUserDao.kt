package com.mc855.app.model.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.mc855.app.model.data.tables.KeycloakUserRoom
import com.mc855.app.model.utils.KC_USER_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface KeycloakUserDao {
	@Query("SELECT * FROM $KC_USER_TABLE WHERE kcId = :kcId")
	fun getUser(kcId: String): Flow<KeycloakUserRoom>

	@Insert(onConflict = REPLACE)
	fun insertUser(keycloakUser: KeycloakUserRoom)

	@Update
	fun updateUser(keycloakUser: KeycloakUserRoom)

	@Delete
	fun deleteUser(keycloakUser: KeycloakUserRoom)
}

