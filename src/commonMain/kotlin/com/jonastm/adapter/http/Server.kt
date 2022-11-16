package com.jonastm.adapter.http

import com.jonastm.Env
import com.jonastm.adapter.http.api.apiRoutes
import com.jonastm.adapter.http.plugins.configureCallID
import com.jonastm.adapter.http.plugins.configureErrorHandling
import com.jonastm.adapter.http.plugins.configureRequestLogging
import com.jonastm.adapter.http.plugins.configureSerialization
import com.jonastm.adapter.persistence.database.NewsRepo
import io.ktor.server.application.*

expect fun startServerWithConfig(config: Env.Http, configuration: Application.() -> Unit)

fun startServer(newsService: NewsRepo, config: Env.Http) {
    startServerWithConfig(config) {
        configure()
        apiRoutes(newsService)
    }
}

fun Application.configure() {
    configureCallID()
    configureErrorHandling()
    configureSerialization()
    configureRequestLogging()
}
