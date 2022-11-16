package com.jonastm

actual fun readEnvironmentVariables(): Env = Env(
    http = Env.Http(
        host = HTTP_HOST,
        port = HTTP_PORT
    ),
    sqlite = Env.Sqlite(
        filePath = "jdbc:sqlite:/Users/jonastm/IdeaProjects/ktor-multi-target-server/db.sqlite"
    )
)
