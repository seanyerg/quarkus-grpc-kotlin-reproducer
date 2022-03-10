package io.quarkus.grpc.examples.hello

import io.quarkus.grpc.GrpcClient
import examples.GreeterGrpc.GreeterBlockingStub
import examples.Greeter
import javax.ws.rs.GET
import examples.HelloReply
import examples.HelloRequest
import io.smallrye.mutiny.Uni
import javax.ws.rs.Path
import javax.ws.rs.PathParam

@Path("/hello")
class HelloWorldEndpoint {
    @GrpcClient("hello")
    var blockingHelloService: GreeterBlockingStub? = null

    @GrpcClient("hello")
    var helloService: Greeter? = null

    @GET
    @Path("/blocking/{name}")
    fun helloBlocking(@PathParam("name") name: String?): String {
        val reply = blockingHelloService!!.sayHello(HelloRequest.newBuilder().setName(name).build())
        return generateResponse(reply)
    }

    @GET
    @Path("/mutiny/{name}")
    fun helloMutiny(@PathParam("name") name: String?): Uni<String> {
        return helloService!!.sayHello(HelloRequest.newBuilder().setName(name).build())
            .onItem().transform { reply: HelloReply -> generateResponse(reply) }
    }

    fun generateResponse(reply: HelloReply): String {
        return String.format("%s! HelloWorldService has been called %d number of times.", reply.message, reply.count)
    }
}