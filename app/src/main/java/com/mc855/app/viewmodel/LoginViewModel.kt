package com.mc855.app.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel() {

	private val _uiState = MutableStateFlow<LoginViewState>(LoginViewState.NotLoggedIn)
	val uiState: StateFlow<LoginViewState> = _uiState

	fun hasValidLoginCredentials(username: String, password: String): Boolean {
		return true
	}

	sealed class LoginViewState() {
		object NotLoggedIn: LoginViewState()
		object LoggedIn: LoginViewState()
		data class LoginFailure(val message: String?): LoginViewState()

	}
}