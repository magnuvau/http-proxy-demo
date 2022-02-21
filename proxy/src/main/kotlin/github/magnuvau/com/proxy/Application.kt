package github.magnuvau.com.proxy

import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.utils.io.*

fun main() {
    embeddedServer(Netty, applicationEngineEnvironment {

        connector {
            port = 8082
            host = "0.0.0.0"
        }

        module {
            val httpClient = HttpClient(Apache) {
                expectSuccess = false
            }

            intercept(ApplicationCallPipeline.Setup) {

                val channel: ByteReadChannel = call.request.receiveChannel()
                val size = channel.availableForRead
                val byteArray = ByteArray(size)
                channel.readFully(byteArray)

                println("Forwarding request to ${call.request.uri}")

                val response = httpClient.request<HttpResponse>(call.request.uri) {
                    method = call.request.httpMethod
                }

                println("Response from server: ${response.readText()}")

                call.respond(
                    object : OutgoingContent.WriteChannelContent() {

                        override val status: HttpStatusCode get() = response.status

                        override suspend fun writeTo(channel: ByteWriteChannel) {
                            response.content.copyAndClose(channel)
                        }
                    }
                )
            }
        }

    }).start(wait = true)
}
