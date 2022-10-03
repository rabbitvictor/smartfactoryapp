package com.mc855.app.view.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.mc855.app.view.destinations.HomeScaffoldDestination
import com.mc855.app.viewmodel.LoginViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay

@RootNavGraph(start = true)
@Destination
@Composable
fun LoginScreen(
	navController: DestinationsNavigator,
	loginViewModel: LoginViewModel = LoginViewModel()
) {
	Surface(
		modifier = Modifier
			.fillMaxWidth()
			.fillMaxHeight(),
		color = MaterialTheme.colors.background
	) {
		when (val state = loginViewModel.uiState.collectAsState().value) {
			is LoginViewModel.LoginViewState.LoggedIn -> AlreadyLogggedInRedirect(navController = navController)
			is LoginViewModel.LoginViewState.LoginFailure -> ErrorDialog(state.message)
			is LoginViewModel.LoginViewState.NotLoggedIn -> LoginForms(
				navController = navController,
				loginViewModel
			)
		}

	}
}


@Composable
fun LoginForms(navController: DestinationsNavigator, loginViewModel: LoginViewModel) {
	Column(
		modifier = Modifier.padding(20.dp),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {

		val username = remember { mutableStateOf(TextFieldValue()) }
		val password = remember { mutableStateOf(TextFieldValue()) }
		val tryLog = remember { mutableStateOf(false) }

		if (tryLog.value) {
			CanLogin(navController = navController, loginViewModel = loginViewModel)
			tryLog.value = false
		}

		Text(text = "Login")

		Spacer(modifier = Modifier.height(20.dp))
		OutlinedTextField(
			label = { Text(text = "Username") },
			leadingIcon = {
				Icon(
					imageVector = Icons.Default.Person,
					contentDescription = "usernameIcon"
				)
			},
			maxLines = 1,
			placeholder = { Text(text = "username") },
			value = username.value,
			onValueChange = { username.value = it }
		)

		Spacer(modifier = Modifier.height(20.dp))
		OutlinedTextField(
			label = { Text(text = "Password") },
			leadingIcon = {
				Icon(
					imageVector = Icons.Default.Key,
					contentDescription = "passwordIcon"
				)
			},
			placeholder = { Text(text = "password") },
			value = password.value,
			visualTransformation = PasswordVisualTransformation(),
			keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
			onValueChange = { password.value = it },
			modifier = Modifier.padding(20.dp)
		)

		Spacer(modifier = Modifier.height(20.dp))
		Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
			Button(
				onClick = { tryLog.value = true },
				shape = RoundedCornerShape(50.dp),
				modifier = Modifier
					.fillMaxWidth()
					.height(50.dp)
			) {
				Text(text = "Login")
			}
		}
	}
}

@Composable
fun CanLogin(navController: DestinationsNavigator, loginViewModel: LoginViewModel) {
	loginViewModel.tryLogin()
	when (val state = loginViewModel.isLoggingAllowed.collectAsState().value) {
		LoginViewModel.IsLoginAllowed.LoginTrue -> navController.navigate(HomeScaffoldDestination)
		LoginViewModel.IsLoginAllowed.LoginFalse -> ErrorDialog(message = "USER LOGIN NOT ALLOWED")
	}
}


@Composable
fun AlreadyLogggedInRedirect(navController: DestinationsNavigator) {
	Column(
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Box {
			Text(text = "Already logged in. Redirecting to sensors screen...")
		}

		LaunchedEffect(Unit) {
			delay(5000)
			navController.navigate(HomeScaffoldDestination)
		}
	}
}

@Composable
private fun ErrorDialog(message: String?) {
	val openDialog = remember { mutableStateOf(true) }
	if (openDialog.value) {
		AlertDialog(
			onDismissRequest = { openDialog.value = false },
			title = { Text("Loading Error!") },
			text = {
				Text(
					""" Error Message:
						$message
					""".trimIndent()
				)
			},
			buttons = {
				Row(
					modifier = Modifier.padding(all = 8.dp),
					horizontalArrangement = Arrangement.Center
				) {
					Button(
						modifier = Modifier.fillMaxWidth(),
						onClick = { openDialog.value = false }
					) {
						Text("Dismiss")
					}
				}

			}
		)
	}
}