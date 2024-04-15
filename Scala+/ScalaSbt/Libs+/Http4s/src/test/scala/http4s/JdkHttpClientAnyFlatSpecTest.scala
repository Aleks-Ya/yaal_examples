package http4s

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import org.http4s.EntityDecoder
import org.http4s.jdkhttpclient.JdkHttpClient
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class JdkHttpClientAnyFlatSpecTest extends AnyFlatSpec with Matchers {
  private val client = JdkHttpClient.simple[IO]

  it should "send GET request" in {
    val statusCode = client.flatMap(c => c.get("https://http4s.org/")(response => IO(response.status.code))).unsafeRunSync()
    statusCode shouldEqual 200
  }

  it should "get status" in {
    val statusCode = client.flatMap(c => c.statusFromString("https://http4s.org/")).unsafeRunSync()
    statusCode.code shouldEqual 200
  }

  it should "get text body" in {
    val body = client.flatMap(c => c.expect("http://httpbin.io/base64/decode/YWJjCg==")(EntityDecoder.text)).unsafeRunSync()
    body shouldEqual "abc\n"
  }

}
