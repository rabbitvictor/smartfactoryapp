package com.mc855.app.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel(): ViewModel() {

	private val _uiState = MutableStateFlow<LoginViewState>(LoginViewState.NotLoggedIn)
	val uiState: StateFlow<LoginViewState> = _uiState
	private val _isLoggingAllowed = MutableStateFlow<IsLoginAllowed>(IsLoginAllowed.LoginFalse)
	val isLoggingAllowed: StateFlow<IsLoginAllowed> = _isLoggingAllowed

	fun tryLogin() {
		try {
			_isLoggingAllowed.value = IsLoginAllowed.LoginTrue
			_uiState.value = LoginViewState.LoggedIn
		} catch (e: Exception) {
			_uiState.value = LoginViewState.LoginFailure(e.message)
			_isLoggingAllowed.value = IsLoginAllowed.LoginFalse
		}
	}

	sealed class LoginViewState() {
		object NotLoggedIn: LoginViewState()
		object LoggedIn: LoginViewState()
		data class LoginFailure(val message: String?): LoginViewState()
	}

	sealed class IsLoginAllowed() {
		object LoginTrue: IsLoginAllowed()
		object LoginFalse: IsLoginAllowed()
	}
}