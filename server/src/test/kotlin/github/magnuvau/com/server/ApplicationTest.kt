package github.magnuvau.com.server

import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication() {
            handleRequest(HttpMethod.Get, "/hello").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Hello, world!", response.content)
            }
        }
    }
}
