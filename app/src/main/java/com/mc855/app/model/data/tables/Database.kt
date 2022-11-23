package com.mc855.app.model.data.tables

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mc855.app.model.data.dao.KeycloakGroupDao
import com.mc855.app.model.data.dao.KeycloakUserDao
import com.mc855.app.model.data.dao.SensorDao
import com.mc855.app.model.data.dao.SensorInfoDao
import com.mc855.app.model.utils.DB_NAME
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(
	entities = [KeycloakUserRoom::class, KeycloakGroupRoom::class, SensorRoom::class, SensorInfoRoom::class],
	version = 5,
	exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
	abstract fun keycloakUserDao(): KeycloakUserDao
	abstract fun keycloakGroupDao(): KeycloakGroupDao
	abstract fun sensorDao(): SensorDao
	abstract fun sensorInfoDao(): SensorInfoDao

	@OptIn(InternalCoroutinesApi::class)
	companion object {
		@Volatile
		private var INSTANCE: AppDatabase? = null

		fun getDatabase(context: Context): AppDatabase {
			val tempInstance = INSTANCE
			if (tempInstance != null) return tempInstance

			synchronized(this) {
				val instance = Room.databaseBuilder(
					context.applicationContext,
					AppDatabase::class.java,
					DB_NAME
				).fallbackToDestructiveMigration().build()

				INSTANCE = instance
				return instance
			}
		}
	}
}