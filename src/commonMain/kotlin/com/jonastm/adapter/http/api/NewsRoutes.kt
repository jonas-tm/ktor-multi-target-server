package com.jonastm.adapter.http.api


import com.jonastm.adapter.http.model.invalidBodyError
import com.jonastm.adapter.http.model.notFoundError
import com.jonastm.adapter.http.model.parseNews
import com.jonastm.adapter.http.model.toResponse
import com.jonastm.adapter.persistence.database.NewsRepo
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

const val NEWS_ID = "newsID"

fun Route.newsRoutes(newsService: NewsRepo) {
    route("/news") {
        get {
            newsService.getAllNews().let {
                call.respond(HttpStatusCode.OK, it.map { it.toResponse() }.toList())
            }
        }
        get("/{$NEWS_ID}") {
            call.parameters[NEWS_ID]?.let {
                val newsID = it.toLong()
                newsService.getNews(newsID)?.let { newsDTO ->
                    call.respond(newsDTO.toResponse())
                } ?: kotlin.run {
                    call.respond(HttpStatusCode.NotFound, notFoundError())
                }
            }
        }
        post {
            val result = call.request.parseNews()
            result.onFailure {
                call.respond(HttpStatusCode.BadRequest, invalidBodyError())
            }
            result.onSuccess {
                val savedEntry = newsService.createNews(it)
                call.respond(savedEntry.toResponse())
            }

        }
    }
}