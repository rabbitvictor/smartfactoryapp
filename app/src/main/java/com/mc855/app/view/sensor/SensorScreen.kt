package com.mc855.app.view.sensor

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lens
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mc855.app.model.network.model.SensorEntity
import com.mc855.app.model.network.model.SensorInfoEntity
import com.mc855.app.model.network.model.UserEntity
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterialApi::class)
@Destination
@Composable
fun SensorScreen(navController: DestinationsNavigator, sensor: SensorInfoEntity) {
	Surface(
		modifier = Modifier
			.fillMaxWidth()
			.fillMaxHeight(),
		color = MaterialTheme.colors.background
	) {
		LazyColumn(
			modifier = Modifier.fillMaxSize(),
		) {
			if (sensor.info != null) {
				items(sensor.info) {
					ListItem(
						text = { Text(it.type) },
						secondaryText = { Text("Valor atual: ${it.lastValue.toString()}${it.unity}") },
						overlineText = { Text(sensor.name) },
						icon = {
							Icon(
								Icons.Filled.Thermostat,
								contentDescription = null,
								modifier = Modifier.size(56.dp),
								tint = MaterialTheme.colors.primary,
							)
						},
					)
				}
			}

		}
	}
}