package com.jonastm.adapter.http

import com.jonastm.Env
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*

actual fun startServerWithConfig(
    config: Env.Http,
    configuration: Application.() -> Unit
) {
    embeddedServer(CIO, port = config.port) {
        configuration()
    }.start(wait = true)
}