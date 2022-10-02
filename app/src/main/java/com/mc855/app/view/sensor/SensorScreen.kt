package com.mc855.app.view.sensor

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lens
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mc855.app.model.network.model.SensorEntity
import com.mc855.app.model.network.model.UserEntity
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterialApi::class)
@Destination
@Composable
fun SensorScreen(navController: DestinationsNavigator, sensor: SensorEntity) {
	Surface(
		modifier = Modifier
			.fillMaxWidth()
			.fillMaxHeight(),
		color = MaterialTheme.colors.background
	) {
		Column(
			modifier = Modifier.fillMaxSize(),
		) {
			Surface(
				elevation = 10.dp
			) {
				ListItem(
					text = { Text(sensor.sensorName) },
					secondaryText = { Text(sensor.id.toString()) },
					icon = {
						Icon(
							Icons.Filled.Lens,
							contentDescription = null,
							modifier = Modifier.size(56.dp)
						)
					},
					modifier = Modifier.clickable {
						navController.navigateUp()
					}
				)
			}
		}
	}
}