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

            get("/greet") {
                log.info("Request to ${call.request.uri}")
                call.respond(HttpStatusCode.OK, "Hello there, what's your name?")
            }

            post("/introduce") {
                log.info("Request to ${call.request.uri}")

                if (!call.request.headers.contains("name")) {
                    call.respond(HttpStatusCode.BadRequest, "I'm sorry?")
                }

                call.respond(HttpStatusCode.OK, "Hello, ${call.request.headers["name"]}!")
            }
        }
    }.start(wait = true)
}
