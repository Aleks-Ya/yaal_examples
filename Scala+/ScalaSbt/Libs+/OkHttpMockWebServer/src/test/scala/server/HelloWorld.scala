package server

import okhttp3.mockwebserver.{MockResponse, MockWebServer}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

import java.net.URL
import scala.io.Source


class HelloWorld extends AnyFlatSpec {

  "Mock Server" should "return responses" in {
    val server = new MockWebServer

    val body1 = "hello, world!"
    val body2 = "sup, bra?"
    val body3 = "yo dog"
    server.enqueue(new MockResponse().setBody(body1))
    server.enqueue(new MockResponse().setBody(body2))
    server.enqueue(new MockResponse().setBody(body3))

    server.start()

    val baseUrl = server.url("/v1/chat/")

    val source1 = Source.fromURL(new URL(baseUrl.url, "messages/"))
    val actBody1 = source1.mkString
    source1.close()
    actBody1 shouldBe body1

    val source2 = Source.fromURL(new URL(baseUrl.url, "messages/2"))
    val actBody2 = source2.mkString
    source2.close()
    actBody2 shouldBe body2

    val source3 = Source.fromURL(new URL(baseUrl.url, "messages/3"))
    val actBody3 = source3.mkString
    source3.close()
    actBody3 shouldBe body3

    val request1 = server.takeRequest
    request1.getPath shouldBe "/v1/chat/messages/"
    request1.getMethod shouldBe "GET"

    val request2 = server.takeRequest
    request2.getPath shouldBe "/v1/chat/messages/2"

    val request3 = server.takeRequest
    request3.getPath shouldBe "/v1/chat/messages/3"

    server.shutdown()
  }
}
