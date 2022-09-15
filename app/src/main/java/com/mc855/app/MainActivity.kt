package com.mc855.app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.DrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lens
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.node.modifierElementOf
import androidx.compose.ui.unit.dp
import com.mc855.app.network.model.UserEntity
import com.mc855.app.network.model.getUsers
import com.mc855.app.ui.theme.SmartFactoryAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			SmartFactoryAppTheme {
				Scaffold()
			}
		}
	}

	@Composable
	private fun Scaffold() {
		val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
		val coroutineScope = rememberCoroutineScope()
		Scaffold(
			scaffoldState = scaffoldState,
			topBar = { TopAppBar(coroutineScope, scaffoldState) },
			drawerContent = { Drawer() },
			floatingActionButton = {
				FloatingActionButton(coroutineScope, scaffoldState)
			}
		) { contentPadding ->
			UsersList(contentPadding)
		}
	}

	@Composable
	private fun FloatingActionButton(
		coroutineScope: CoroutineScope,
		scaffoldState: ScaffoldState
	) {
		FloatingActionButton(
			onClick = {
				coroutineScope.launch {
					when (scaffoldState.snackbarHostState.showSnackbar(
						message = "Snack Bar",
						actionLabel = "Dismiss"
					)) {
						SnackbarResult.ActionPerformed -> TODO()
						SnackbarResult.Dismissed -> TODO()
					}
				}
			},
		) {
			Icon(Icons.Filled.Add, "")
		}
	}

	@Composable
	private fun Drawer() {
		Text("Drawer", modifier = Modifier.padding(16.dp))
		Divider()
		Row() {

		}
	}
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UsersList(contentPadding: PaddingValues) {
	Log.d("xapp-called", "Compose")

	val users = produceState(
		initialValue = listOf<UserEntity>(),
		producer = {
			value = getUsers()
			Log.d("xapp-called", "Producer")
		}
	)
	LazyColumn(
		Modifier.fillMaxSize(),
		contentPadding = contentPadding,
		verticalArrangement = Arrangement.spacedBy(10.dp)
	) {
		items(users.value) { user ->
			Surface(
				elevation = 10.dp
			) {
				ListItem(
					text = {
						Text(user.title)
					},
					secondaryText = {
						Text(user.id)
					},
					icon = {
						Icon(
							Icons.Filled.Lens,
							contentDescription = null,
							modifier = Modifier.size(56.dp),
							tint = chooseColor(user.completed),
						)
					}
				)
			}
		}
	}
}

@Composable
fun TopAppBar(scope: CoroutineScope, scaffoldState: ScaffoldState) {
	TopAppBar(
		title = { Text(text = "Lista de Sensores") },
		modifier = Modifier.clickable {
			scope.launch {
				scaffoldState.drawerState.open()
			}
		}
	)
}


@Composable
private fun chooseColor(hasCompleted: Boolean) =
	if (hasCompleted) MaterialTheme.colors.primary else MaterialTheme.colors.secondary