package com.mc855.app.network.client

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

object KtorClient {
	val httpClient = HttpClient(Android) {
		install(ContentNegotiation) {
			json()
		}

		install(Logging) {
			logger = object : Logger {
				override fun log(message: String) {
					Log.v("Logger Ktor =>", message)
				}
			}

			level = LogLevel.ALL
		}

		install(ResponseObserver) {
			onResponse { response ->
				Log.d("HTTP status:", "${response.status.value}")
			}
		}

		install(DefaultRequest) {
			header(HttpHeaders.ContentType, ContentType.Application.Json)
		}


	}
}
