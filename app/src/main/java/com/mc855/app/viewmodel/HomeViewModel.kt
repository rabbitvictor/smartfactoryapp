package com.mc855.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mc855.app.model.network.SensorRepository
import com.mc855.app.model.network.UserRepository
import com.mc855.app.model.network.model.SensorEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
	private val sensorRepository: SensorRepository = SensorRepository
) : ViewModel() {
	private val _uiState = MutableStateFlow<HomeViewState>(HomeViewState.Empty)
	val uiState: StateFlow<HomeViewState> = _uiState

	init {
		fetchSensors()
	}

	private fun fetchSensors() {
		_uiState.value = HomeViewState.Loading
		viewModelScope.launch(Dispatchers.IO) {
			try {
				val sensorsList = sensorRepository.fetchSensors()
				_uiState.value = HomeViewState.SensorsListLoaded(sensorsList)
			} catch (ex: Exception) {
				_uiState.value = HomeViewState.SensorsListFailure(ex.message)
			}
		}
	}


	sealed class HomeViewState {
		object Empty: HomeViewState()
		object Loading: HomeViewState()
		data class SensorsListLoaded(val usersList: List<SensorEntity>) : HomeViewState()
		data class SensorsListFailure(val message: String?) : HomeViewState()
	}
}