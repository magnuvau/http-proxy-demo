package github.magnuvau.com.client

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.apache.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking

fun main() {
    val response = rest("hello")
    runBlocking {
        println("Response: ${response.status} - ${response.readText()}")
    }
    Thread.sleep(1000)
}

fun rest(path: String) : HttpResponse {
    val httpClient = HttpClient(Apache) {
        engine {
            proxy = ProxyBuilder.http("http://localhost:8082/")
        }
        expectSuccess = false
    }

    val response = runBlocking {
        httpClient.get<HttpResponse>("http://localhost:8083/$path")
    }

    httpClient.close()

    return response
}
