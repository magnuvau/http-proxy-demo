package github.magnuvau.com.client

import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*

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
}
