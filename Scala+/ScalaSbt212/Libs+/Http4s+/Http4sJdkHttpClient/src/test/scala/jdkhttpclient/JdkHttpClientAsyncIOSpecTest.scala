package jdkhttpclient

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import org.http4s.EntityDecoder
import org.http4s.jdkhttpclient.JdkHttpClient
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

class JdkHttpClientAsyncIOSpecTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  private val clientResource = JdkHttpClient.simple[IO]

  "JdkHttpClient" - {
    "send GET request" in {
      clientResource.use(_.get("https://http4s.org/")(response => IO(response.status.code)))
        .asserting(_ shouldEqual 200)
    }

    "get status" in {
      clientResource.use(_.statusFromString("https://http4s.org/"))
        .asserting(_.code shouldEqual 200)
    }

    "get text body" in {
      clientResource.use(_.expect("http://httpbin.io/base64/YWJjCg==")(EntityDecoder.text))
        .asserting(_ shouldEqual "abc\n")
    }
  }
}