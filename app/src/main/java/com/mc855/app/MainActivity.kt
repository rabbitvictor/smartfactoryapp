package com.mc855.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mc855.app.model.data.tables.AppDatabase
import com.mc855.app.ui.theme.SmartFactoryAppTheme
import com.mc855.app.view.NavGraphs
import com.mc855.app.viewmodel.HomeViewModel
import com.mc855.app.viewmodel.LoginViewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val database = AppDatabase.getDatabase(this)
		val homeViewModel by lazy { HomeViewModel(database) }
		val loginViewModel by lazy { LoginViewModel(database) }

		setContent {
			SmartFactoryAppTheme {
				DestinationsNavHost(navGraph = NavGraphs.root,
				dependenciesContainerBuilder = {
					this.dependency(homeViewModel)
					this.dependency(loginViewModel)
				})
			}
		}
	}
}