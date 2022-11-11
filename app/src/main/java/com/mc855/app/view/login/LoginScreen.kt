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
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.mc855.app.view.destinations.HomeScaffoldDestination
import com.mc855.app.viewmodel.LoginViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ExperimentalComposeUiApi
@RootNavGraph(start = true)
@Destination
@Composable
fun LoginScreen(navController: DestinationsNavigator, loginViewModel: LoginViewModel) {
	Surface(
		modifier = Modifier
			.fillMaxWidth()
			.fillMaxHeight(),
		color = MaterialTheme.colors.background
	) {
		Column(
			modifier = Modifier.padding(20.dp),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {

			when (val state = loginViewModel.uiState.collectAsState().value) {
				is LoginViewModel.LoginViewState.LoggedIn -> navController.navigate(
					HomeScaffoldDestination
				)
				is LoginViewModel.LoginViewState.LoginFailure -> ErrorDialog(
					state.message,
					loginViewModel
				)
				LoginViewModel.LoginViewState.NotLoggedIn -> println("Não está logado")
			}

			val username = remember { mutableStateOf(TextFieldValue()) }
			val password = remember { mutableStateOf(TextFieldValue()) }
			val focusManager = LocalFocusManager.current

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
				onValueChange = { username.value = it },
				keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
				keyboardOptions = KeyboardOptions.Default.copy(
					imeAction = ImeAction.Done,
					keyboardType = KeyboardType.Password
				),
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
					onClick = {
						loginViewModel.hasValidLoginCredentials(
							username.value.text,
							password.value.text
						)
					},
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
}

@Composable
private fun ErrorDialog(message: String?, loginViewModel: LoginViewModel) {
	val openDialog = remember { mutableStateOf(true) }
	if (openDialog.value) {
		AlertDialog(
			onDismissRequest = { openDialog.value = false },
			title = { Text("Login Error!") },
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
						onClick = {
							loginViewModel.updateLoginViewState()
							openDialog.value = false
						}
					) {
						Text("Dismiss")
					}
				}

			}
		)
	}
}