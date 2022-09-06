package com.mc855.app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mc855.app.http.model.UserEntity
import com.mc855.app.http.model.getUsers
import com.mc855.app.ui.theme.SmartFactoryAppTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			SmartFactoryAppTheme {
				UsersList()
			}
		}
	}
}

@Composable
fun UsersList() {
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
		contentPadding = PaddingValues(12.dp),
		verticalArrangement = Arrangement.spacedBy(12.dp)
	) {
		items(users.value) { user ->
			Card(
				modifier = Modifier.fillMaxWidth(),
				elevation = 2.dp,
				backgroundColor = if (user.completed) Color(0xFFB6F1B8) else Color.White
			) {
				Row(
					Modifier.padding(12.dp),
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.spacedBy(12.dp)
				) {

					Box(
						Modifier
							.clip(CircleShape)
							.background(Color(0xFF4CAF50))
							.size(48.dp),
						contentAlignment = Alignment.Center
					) {
						Text(text = user.id)
					}
					Text(text = user.title)
				}
			}
		}

	}
}