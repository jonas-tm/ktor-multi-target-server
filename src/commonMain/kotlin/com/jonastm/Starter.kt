package com.jonastm

import com.jonastm.adapter.http.startServer
import com.jonastm.adapter.persistence.database.initRepo

fun starter() {
    val env = readEnvironmentVariables()

    val repo = initRepo(env.sqlite)
    startServer(repo.newsRepo, env.http)
}