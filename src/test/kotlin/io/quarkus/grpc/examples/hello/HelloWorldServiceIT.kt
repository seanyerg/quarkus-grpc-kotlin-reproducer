package io.quarkus.grpc.examples.hello

import io.quarkus.test.junit.NativeImageTest
import io.quarkus.grpc.examples.hello.HelloWorldEndpointTest

@NativeImageTest
internal class HelloWorldServiceIT : HelloWorldEndpointTest()