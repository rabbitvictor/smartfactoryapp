package com.mc855.app.model.data.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mc855.app.model.utils.KC_GROUP_TABLE


@Entity(tableName = KC_GROUP_TABLE)
data class KeycloakGroupRoom(
	@PrimaryKey(autoGenerate = true) val id: Int,
	val kcId: String,
	val name: String,
	val path: String
)
