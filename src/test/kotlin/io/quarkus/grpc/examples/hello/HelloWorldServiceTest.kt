package io.quarkus.grpc.examples.hello

import io.quarkus.test.junit.QuarkusTest
import io.grpc.ManagedChannel
import org.junit.jupiter.api.BeforeEach
import io.grpc.ManagedChannelBuilder
import org.junit.jupiter.api.AfterEach
import kotlin.Throws
import java.lang.InterruptedException
import java.util.concurrent.TimeUnit
import examples.GreeterGrpc.GreeterBlockingStub
import examples.GreeterGrpc
import examples.HelloReply
import examples.HelloRequest
import examples.MutinyGreeterGrpc
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.time.Duration

@QuarkusTest
internal class HelloWorldServiceTest {
    private var channel: ManagedChannel? = null
    @BeforeEach
    fun init() {
        channel = ManagedChannelBuilder.forAddress("localhost", 9001).usePlaintext().build()
    }

    @AfterEach
    @Throws(InterruptedException::class)
    fun cleanup() {
        channel!!.shutdown()
        channel!!.awaitTermination(10, TimeUnit.SECONDS)
    }

    @Test
    fun testHelloWorldServiceUsingBlockingStub() {
        val client = GreeterGrpc.newBlockingStub(channel)
        val reply = client
            .sayHello(HelloRequest.newBuilder().setName("neo-blocking").build())
        Assertions.assertThat(reply.message).isEqualTo("Hello neo-blocking")
    }

    @Test
    fun testHelloWorldServiceUsingMutinyStub() {
        val reply = MutinyGreeterGrpc.newMutinyStub(channel)
            .sayHello(HelloRequest.newBuilder().setName("neo-blocking").build())
            .await().atMost(Duration.ofSeconds(5))
        Assertions.assertThat(reply.message).isEqualTo("Hello neo-blocking")
    }
}