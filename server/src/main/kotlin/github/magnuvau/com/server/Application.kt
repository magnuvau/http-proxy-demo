package github.magnuvau.com.server

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.network.tls.certificates.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.io.File

fun main() {

    embeddedServer(Netty, applicationEngineEnvironment {

        val keyStoreFile = File("src/main/resources/keystore.jks")
        val keystore = generateCertificate(
            file = keyStoreFile,
            keyAlias = "serverKeystore",
            keyPassword = "password1234",
            jksPassword = "password1234"
        )

        sslConnector(
            keyStore = keystore,
            keyAlias = "serverKeystore",
            keyStorePassword = { "password1234".toCharArray() },
            privateKeyPassword = { "password1234".toCharArray() }) {
            port = 8083
            keyStorePath = keyStoreFile
        }

        module {

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
        }
    }).start(wait = true)
}
