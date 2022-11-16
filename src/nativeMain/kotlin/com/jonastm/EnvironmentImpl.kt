package com.jonastm

import kotlinx.cinterop.toKString
import platform.posix.getenv

actual fun readEnvironmentVariables(): Env = Env(
    http = Env.Http(
        host = getenv("SERVER_HOST")?.toKString() ?: HTTP_HOST,
        port = getenv("SERVER_PORT")?.toKString()?.toIntOrNull() ?: HTTP_PORT
    ),
    sqlite = Env.Sqlite(
        filePath = "test.sqlite"
     )
)
