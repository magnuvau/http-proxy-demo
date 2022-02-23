package github.magnuvau.com.client

import io.ktor.client.statement.*
import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*
import kotlinx.coroutines.runBlocking

class ApplicationTest {
    @Test
    fun hello() {
        withApplication {
            val response = rest("hello")
            assertEquals(HttpStatusCode.OK, response.status)
        }
    }

    @Test
    fun bad() {
        withApplication {
            val response = rest("bad")
            assertEquals(HttpStatusCode.InternalServerError, response.status)
        }
    }

    @Test
    fun name() {
        withApplication {
            val response = name("name")
            assertEquals(HttpStatusCode.OK, response.status)
            runBlocking {
                assertEquals("Hello, Slim Shady!", response.readText())
            }
        }
    }
}
