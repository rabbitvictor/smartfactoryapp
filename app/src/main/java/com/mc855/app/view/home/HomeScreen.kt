package com.mc855.app.view.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.DrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lens
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mc855.app.model.network.model.SensorEntity
import com.mc855.app.model.network.model.SensorInfoEntity
import com.mc855.app.model.network.model.hasExceededLimit
import com.mc855.app.view.destinations.SensorScreenDestination
import com.mc855.app.viewmodel.HomeViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Destination
@Composable
fun HomeScaffold(
	navController: DestinationsNavigator,
	homeViewModel: HomeViewModel,
) {
	val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
	val coroutineScope = rememberCoroutineScope()
	Surface(
		modifier = Modifier
			.fillMaxWidth()
			.fillMaxHeight(),
		color = MaterialTheme.colors.background
	) {
		Scaffold(
			scaffoldState = scaffoldState,
			topBar = { TopAppBar(coroutineScope, scaffoldState) },
//			drawerContent = { Drawer() },
			floatingActionButton = { FloatingActionButton(coroutineScope, scaffoldState, homeViewModel) },
			isFloatingActionButtonDocked = true
		) { contentPadding ->
			HomeScreenList(contentPadding, navController, homeViewModel)
		}
	}
}

@Composable
private fun FloatingActionButton(
	coroutineScope: CoroutineScope,
	scaffoldState: ScaffoldState,
	homeViewModel: HomeViewModel,
) {
	androidx.compose.material.FloatingActionButton(
		onClick = {
			homeViewModel.fetchSensorInfo()
		},
	) {
		Icon(Icons.Filled.Refresh, "")
	}
}

@Composable
private fun Drawer() {
	Text("Drawer", modifier = Modifier.padding(16.dp))
	Divider()
	Row() {

	}
}


@Composable
private fun TopAppBar(scope: CoroutineScope, scaffoldState: ScaffoldState) {
	androidx.compose.material.TopAppBar(
		title = { Text(text = "Lista de Sensores") },
		modifier = Modifier.clickable {
			scope.launch {
				scaffoldState.drawerState.open()
			}
		}
	)
}

@Composable
private fun HomeScreenList(
	contentPadding: PaddingValues,
	navController: DestinationsNavigator,
	homeViewModel: HomeViewModel
) {
	when (val state = homeViewModel.uiState.collectAsState().value) {
		is HomeViewModel.HomeViewState.SensorsInfoListLoaded -> SensorsListComposable(
			contentPadding = contentPadding,
			sensorsList = state.list,
			navController
		)

		is HomeViewModel.HomeViewState.Empty, is HomeViewModel.HomeViewState.Loading -> SensorsListComposable(
			contentPadding,
			emptyList(),
			navController
		)

		is HomeViewModel.HomeViewState.SensorsListFailure -> errorDialog(message = state.message)
		is HomeViewModel.HomeViewState.DbTesteSuccess -> {
			state.groupList.forEach {
				println(it)
			}
		}

	}
}

@Composable
private fun errorDialog(message: String?) {
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


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SensorsListComposable(
	contentPadding: PaddingValues,
	sensorsList: List<SensorInfoEntity>,
	navController: DestinationsNavigator
) {
	LazyColumn(
		Modifier.fillMaxSize(),
		contentPadding = contentPadding,
		verticalArrangement = Arrangement.spacedBy(10.dp)
	) {
		items(sensorsList) { sensor ->
			Surface(
				elevation = 10.dp
			) {
				ListItem(
					text = {
						Text(sensor.name)
					},
					icon = {
						Icon(
							Icons.Filled.Lens,
							contentDescription = null,
							modifier = Modifier.size(56.dp),
							tint = chooseColor(sensor.hasExceededLimit()),
						)
					},
					modifier = Modifier.clickable {
						navController.navigate(SensorScreenDestination(sensor))
					}
				)
			}
		}
	}
}

@Composable
private fun chooseColor(hasCompleted: Boolean) =
	if (hasCompleted) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
