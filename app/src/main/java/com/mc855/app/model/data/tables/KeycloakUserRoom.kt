package com.mc855.app.model.data.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mc855.app.model.utils.KC_USER_TABLE

@Entity(tableName = KC_USER_TABLE)
data class KeycloakUserRoom(
	@PrimaryKey(autoGenerate = true) val id: Int,
	val kcId: String,
	val username: String,
	val firstName: String?,
	val lastName: String?,
	val createdTimestap: Int,
	val groups: String,
	val token: String
)