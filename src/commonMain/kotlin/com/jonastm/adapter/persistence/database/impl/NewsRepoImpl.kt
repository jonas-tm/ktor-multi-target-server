package com.jonastm.adapter.persistence.database.impl

import com.jonastm.adapter.persistence.database.NewsRepo
import com.jonastm.dto.NewsDTO
import com.jonastm.orm.sqldelight.DB

class NewsRepoImpl(val db: DB) : NewsRepo {

    val mapper = { id: Long, title: String, text: String -> NewsDTO(id, title, text) }

    override suspend fun getAllNews(): List<NewsDTO> {
        return db.newsQueries.findAll(mapper).executeAsList()
    }

    override suspend fun createNews(newsEntry: NewsDTO): NewsDTO {
        var id: Long? = null
        db.newsQueries.transaction {
            db.newsQueries.insert(newsEntry.title, newsEntry.text)
            id = db.newsQueries.rowid().executeAsOne()
        }

        id?.let {
            return newsEntry.copy(id = it)
        }
        throw Exception("coult not get id of isnert")
    }

    override suspend fun getNews(id: Long): NewsDTO? {
        return db.newsQueries.selectById(id, mapper).executeAsOneOrNull()
    }

}