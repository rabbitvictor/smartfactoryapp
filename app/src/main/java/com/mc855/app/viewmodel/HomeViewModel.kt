package com.mc855.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mc855.app.model.data.tables.AppDatabase
import com.mc855.app.model.data.tables.KeycloakGroupRoom
import com.mc855.app.model.network.SensorInfoRepo
import com.mc855.app.model.network.SensorInfoRepo.getSensorInfoList
import com.mc855.app.model.network.SensorRepository
import com.mc855.app.model.network.model.SensorEntity
import com.mc855.app.model.network.model.SensorInfoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch

class HomeViewModel(
	val database: AppDatabase,
	private val sensorRepository: SensorRepository = SensorRepository,
) : ViewModel() {
	private val _uiState = MutableStateFlow<HomeViewState>(HomeViewState.Empty)
	val uiState: StateFlow<HomeViewState> = _uiState

	fun fetchSensorInfo() {
		_uiState.value = HomeViewState.Loading
		viewModelScope.launch(Dispatchers.IO) {
			runCatching {
				val token = database.keycloakUserDao().getAllUsers().last().token
				val sensorInfoList = getSensorInfoList(token)
				_uiState.value = HomeViewState.SensorsInfoListLoaded(sensorInfoList)
			}.onFailure { _uiState.value = HomeViewState.SensorsListFailure(it.message) }
		}
	}

//	fun fetchSensors() {
//		_uiState.value = HomeViewState.Loading
//		viewModelScope.launch(Dispatchers.IO) {
//			try {
//				val sensorsList = sensorRepository.fetchSensors()
//				_uiState.value = HomeViewState.SensorsListLoaded(sensorsList)
//			} catch (ex: Exception) {
//				_uiState.value = HomeViewState.SensorsListFailure(ex.message)
//			}
//		}
//	}

	fun testDb() {
		_uiState.value = HomeViewState.Loading
		viewModelScope.launch(Dispatchers.IO) {
			runCatching {
				database.keycloakGroupDao().insertGroup(
					KeycloakGroupRoom(
						1,
						"teste",
						"teste group",
						"/group"
					)
				)
				val groupList = database.keycloakGroupDao().getAllGroups().single()

			}
		}
	}


	sealed class HomeViewState {
		object Empty : HomeViewState()
		object Loading : HomeViewState()
//		data class SensorsListLoaded(val usersList: List<SensorEntity>) : HomeViewState()
		data class SensorsListFailure(val message: String?) : HomeViewState()
		data class DbTesteSuccess(val groupList: List<KeycloakGroupRoom>) : HomeViewState()
		data class SensorsInfoListLoaded(val list: List<SensorInfoEntity>) : HomeViewState()
	}
}