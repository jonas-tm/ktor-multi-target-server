package com.jonastm

expect fun readEnvironmentVariables(): Env

// Defaults
const val HTTP_HOST: String = "0.0.0.0"
const val HTTP_PORT: Int = 8080

data class Env(
    val sqlite: Sqlite,
    val http: Http
) {
    data class Http(
        val host: String,
        val port: Int
    )

    data class Sqlite(
        val filePath: String
    )
}