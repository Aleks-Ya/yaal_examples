package jdkhttpclient

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import org.http4s.EntityDecoder
import org.http4s.jdkhttpclient.JdkHttpClient
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

class JdkHttpClientAsyncIOSpecTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  private val client = JdkHttpClient.simple[IO]
  "JdkHttpClient" - {
    "send GET request" in {
      client.flatMap(c => c.get("https://http4s.org/")(response => IO(response.status.code)))
    }.asserting(_ shouldEqual 200)

    "get status" in {
      client.flatMap(c => c.statusFromString("https://http4s.org/"))
    }.asserting(_.code shouldEqual 200)

    "get text body" in {
      client.flatMap(c => c.expect("http://httpbin.io/base64/decode/YWJjCg==")(EntityDecoder.text))
    }.asserting(_ shouldEqual "abc\n")
  }
}
