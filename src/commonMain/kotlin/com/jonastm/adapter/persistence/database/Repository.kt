package com.jonastm.adapter.persistence.database

import com.jonastm.Env
import com.jonastm.adapter.persistence.database.impl.NewsRepoImpl
import com.jonastm.dto.NewsDTO
import com.jonastm.orm.sqldelight.DB
import com.squareup.sqldelight.db.SqlDriver

expect fun createDriver(config: Env.Sqlite): SqlDriver

fun initRepo(config: Env.Sqlite): Repositories {
    val db = initDB(config)
    return Repositories(
        newsRepo = NewsRepoImpl(db)
    )
}

fun initDB(config: Env.Sqlite): DB {
    val driver = createDriver(config)
    DB.Schema.create(driver)
    DB.Schema.migrate(driver, 0, 1)
    return DB(driver)
}


data class Repositories(
    val newsRepo: NewsRepo
)

interface NewsRepo {
    suspend fun getAllNews(): List<NewsDTO>
    suspend fun createNews(newsEntry: NewsDTO): NewsDTO
    suspend fun getNews(id: Long): NewsDTO?
}