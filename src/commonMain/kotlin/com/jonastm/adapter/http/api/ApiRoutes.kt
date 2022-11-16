package com.jonastm.adapter.http.api

import com.jonastm.adapter.persistence.database.NewsRepo
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.apiRoutes(newsService: NewsRepo) {
    routing {
        route("/api/v1") {
            newsRoutes(newsService)
        }
    }
}