package com.jonastm.adapter.http

import com.jonastm.Env
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

actual fun startServerWithConfig(
    config: Env.Http,
    configuration: Application.() -> Unit
) {
    embeddedServer(Netty, port = config.port) {
        configuration()
    }.start(wait = true)
}