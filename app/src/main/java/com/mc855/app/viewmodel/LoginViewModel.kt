package com.mc855.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mc855.app.model.data.tables.AppDatabase
import com.mc855.app.model.data.tables.KeycloakUserRoom
import com.mc855.app.model.network.KeycloakRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
	val database: AppDatabase,
	val keycloakRepository: KeycloakRepository = KeycloakRepository
) :
	ViewModel() {

	private val _uiState = MutableStateFlow<LoginViewState>(LoginViewState.NotLoggedIn)
	val uiState: StateFlow<LoginViewState> = _uiState

	fun hasValidLoginCredentials(username: String, password: String) {
		_uiState.value = LoginViewState.NotLoggedIn
		viewModelScope.launch(Dispatchers.IO) {
			runCatching {
				val keycloakUserEntity = keycloakRepository.authUser(username, password)
				val groups = buildString {
					val pathList = keycloakUserEntity.groups.map { it.path }
					pathList.forEach { path -> append(path) }
				}

				val keycloakUserRoom = KeycloakUserRoom(
					kcId = keycloakUserEntity.kcId,
					username = keycloakUserEntity.username,
					firstName = keycloakUserEntity.firstName,
					lastName = keycloakUserEntity.lastName,
					groups = groups,
					token = keycloakUserEntity.token
				)
				database.keycloakUserDao().insertUser(keycloakUserRoom)
			}.onFailure {
				_uiState.value = LoginViewState.LoginFailure(it.message)
			}.onSuccess { _uiState.value = LoginViewState.LoggedIn }
		}
	}

	fun updateLoginViewState() {
		_uiState.value = LoginViewState.NotLoggedIn
	}

	sealed class LoginViewState() {
		object NotLoggedIn : LoginViewState()
		object LoggedIn : LoginViewState()
		data class LoginFailure(val message: String?) : LoginViewState()
	}
}