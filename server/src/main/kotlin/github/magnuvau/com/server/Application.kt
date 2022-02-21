package github.magnuvau.com.server

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8083) {

        routing {
            get("/hello") {
                println("Request from ${call.request.uri}")
                call.respond("Hello, world!")
            }

            get("/bad") {
                call.respond(HttpStatusCode.InternalServerError, "Error!")
            }
        }

    }.start(wait = true)
}
