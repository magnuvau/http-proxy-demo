package github.magnuvau.com.client

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.apache.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    val greetResponse = greet()
    val introduceResponse = introduce(args.first())

    runBlocking {
        println("Hello!")
        println(greetResponse.readText())

        println("My name is ${args.first()}!")
        println(introduceResponse.readText())
    }
}

fun greet() : HttpResponse {
    val httpClient = HttpClient(Apache) {
        engine {
            proxy = ProxyBuilder.http("http://localhost:8082/")
        }
        expectSuccess = false
    }

    val response = runBlocking {
        httpClient.get<HttpResponse>("http://localhost:8083/greet")
    }

    httpClient.close()

    return response
}

fun introduce(name: String) : HttpResponse {
    val httpClient = HttpClient(Apache) {
        engine {
            proxy = ProxyBuilder.http("http://localhost:8082/")
        }
        expectSuccess = false
    }

    val response = runBlocking {
        httpClient.post<HttpResponse>("http://localhost:8083/introduce") {
            headers {
                header("name", name)
            }
        }
    }

    httpClient.close()

    return response
}
