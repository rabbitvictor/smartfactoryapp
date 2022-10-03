package com.mc855.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.mc855.app.ui.theme.SmartFactoryAppTheme
import com.mc855.app.view.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			SmartFactoryAppTheme {
				DestinationsNavHost(navGraph = NavGraphs.root)
			}
		}
	}
}