package io.quarkus.grpc.examples.hello

import io.quarkus.grpc.GrpcService
import examples.Greeter
import java.util.concurrent.atomic.AtomicInteger
import io.smallrye.mutiny.Uni
import examples.HelloReply
import examples.HelloRequest

@GrpcService
class HelloWorldService : Greeter {
    var counter = AtomicInteger()
    override fun sayHello(request: HelloRequest): Uni<HelloReply> {
        val count = counter.incrementAndGet()
        val name = request.name
        return Uni.createFrom().item("Hi $name")
            .map { res: String? -> HelloReply.newBuilder().setMessage(res).setCount(count).build() }
    }
}