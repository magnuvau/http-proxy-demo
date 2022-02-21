package github.magnuvau.com.client

import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*

class ApplicationTest {
    @Test
    fun testRoot() {
        withApplication {
            val response = rest("hello")
            assertEquals(HttpStatusCode.OK, response.status)
        }
    }
}
