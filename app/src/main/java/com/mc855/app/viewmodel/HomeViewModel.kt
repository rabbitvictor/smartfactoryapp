package com.mc855.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mc855.app.model.network.SensorRepository
import com.mc855.app.model.network.SensorRepository.fetchSensors
import com.mc855.app.model.network.UserRepository
import com.mc855.app.model.network.model.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
	private val userRepository: UserRepository = UserRepository,
	private val sensorRepository: SensorRepository = SensorRepository
) : ViewModel() {
	private val _uiState = MutableStateFlow<HomeViewState>(HomeViewState.Empty)
	val uiState: StateFlow<HomeViewState> = _uiState

	init {
		fetchUsers()
	}

	private fun fetchUsers() {
		_uiState.value = HomeViewState.Loading
		viewModelScope.launch(Dispatchers.IO) {
			try {
				val usersList = userRepository.fetchUsers()
				_uiState.value = HomeViewState.UsersListLoaded(usersList)
			} catch (ex: Exception) {
				_uiState.value = HomeViewState.UsersListLoadFailure(ex.message)
			}
		}
	}


	sealed class HomeViewState {
		object Empty: HomeViewState()
		object Loading: HomeViewState()
		data class UsersListLoaded(val usersList: List<UserEntity>) : HomeViewState()
		data class UsersListLoadFailure(val message: String?) : HomeViewState()
	}
}