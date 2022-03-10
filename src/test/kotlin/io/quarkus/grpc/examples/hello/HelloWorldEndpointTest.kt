package io.quarkus.grpc.examples.hello

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

@QuarkusTest
internal open class HelloWorldEndpointTest {
    @Test
    fun testHelloWorldServiceUsingBlockingStub() {
        val response = RestAssured.get("/hello/blocking/neo").asString()
        Assertions.assertThat(response).startsWith("Hello neo")
    }

    @Test
    fun testHelloWorldServiceUsingMutinyStub() {
        val response = RestAssured.get("/hello/mutiny/neo-mutiny").asString()
        Assertions.assertThat(response).startsWith("Hello neo-mutiny")
    }
}